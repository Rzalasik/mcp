package br.edu.faculdade.clinicaveterinaria.repository;

import br.edu.faculdade.clinicaveterinaria.model.Animal;
import br.edu.faculdade.clinicaveterinaria.model.Tutor;
import br.edu.faculdade.clinicaveterinaria.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnimalRepository {

    public Animal salvar(Animal animal) {
        String sql = "INSERT INTO animal (nome, especie, raca, id_tutor) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getEspecie());
            stmt.setString(3, animal.getRaca());
            stmt.setInt(4, animal.getTutor().getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) animal.setId(rs.getInt("id"));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar animal: " + e.getMessage(), e);
        }
        return animal;
    }

    public Optional<Animal> buscarPorId(int id) {
        String sql = """
                SELECT a.id, a.nome, a.especie, a.raca,
                       t.id AS tutor_id, t.nome AS tutor_nome, t.endereco, t.telefone
                FROM animal a
                JOIN tutor t ON t.id = a.id_tutor
                WHERE a.id = ?
                """;
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar animal: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public List<Animal> listarTodos() {
        String sql = """
                SELECT a.id, a.nome, a.especie, a.raca,
                       t.id AS tutor_id, t.nome AS tutor_nome, t.endereco, t.telefone
                FROM animal a
                JOIN tutor t ON t.id = a.id_tutor
                ORDER BY a.nome
                """;
        List<Animal> lista = new ArrayList<>();
        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar animais: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<Animal> listarPorTutor(int idTutor) {
        String sql = """
                SELECT a.id, a.nome, a.especie, a.raca,
                       t.id AS tutor_id, t.nome AS tutor_nome, t.endereco, t.telefone
                FROM animal a
                JOIN tutor t ON t.id = a.id_tutor
                WHERE a.id_tutor = ?
                """;
        List<Animal> lista = new ArrayList<>();
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idTutor);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar animais por tutor: " + e.getMessage(), e);
        }
        return lista;
    }

    public void atualizar(Animal animal) {
        String sql = "UPDATE animal SET nome = ?, especie = ?, raca = ?, id_tutor = ? WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getEspecie());
            stmt.setString(3, animal.getRaca());
            stmt.setInt(4, animal.getTutor().getId());
            stmt.setInt(5, animal.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar animal: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM animal WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar animal: " + e.getMessage(), e);
        }
    }

    private Animal mapear(ResultSet rs) throws SQLException {
        Tutor tutor = new Tutor();
        tutor.setId(rs.getInt("tutor_id"));
        tutor.setNome(rs.getString("tutor_nome"));
        tutor.setEndereco(rs.getString("endereco"));
        tutor.setTelefone(rs.getString("telefone"));

        Animal a = new Animal();
        a.setId(rs.getInt("id"));
        a.setNome(rs.getString("nome"));
        a.setEspecie(rs.getString("especie"));
        a.setRaca(rs.getString("raca"));
        a.setTutor(tutor);
        return a;
    }
}
