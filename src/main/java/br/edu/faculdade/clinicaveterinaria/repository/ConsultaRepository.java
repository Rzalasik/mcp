package br.edu.faculdade.clinicaveterinaria.repository;

import br.edu.faculdade.clinicaveterinaria.model.Animal;
import br.edu.faculdade.clinicaveterinaria.model.Consulta;
import br.edu.faculdade.clinicaveterinaria.model.Tutor;
import br.edu.faculdade.clinicaveterinaria.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsultaRepository {

    public Consulta salvar(Consulta consulta) {
        String sql = "INSERT INTO consulta (id_animal, data, motivo, valor) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, consulta.getAnimal().getId());
            stmt.setDate(2, Date.valueOf(consulta.getData()));
            stmt.setString(3, consulta.getMotivo());
            stmt.setBigDecimal(4, consulta.getValor());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) consulta.setId(rs.getInt("id"));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar consulta: " + e.getMessage(), e);
        }
        return consulta;
    }

    public Optional<Consulta> buscarPorId(int id) {
        String sql = """
                SELECT c.id, c.data, c.motivo, c.valor,
                       a.id AS animal_id, a.nome AS animal_nome, a.especie, a.raca,
                       t.id AS tutor_id, t.nome AS tutor_nome, t.endereco, t.telefone
                FROM consulta c
                JOIN animal a ON a.id = c.id_animal
                JOIN tutor  t ON t.id = a.id_tutor
                WHERE c.id = ?
                """;
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar consulta: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public List<Consulta> listarTodos() {
        String sql = """
                SELECT c.id, c.data, c.motivo, c.valor,
                       a.id AS animal_id, a.nome AS animal_nome, a.especie, a.raca,
                       t.id AS tutor_id, t.nome AS tutor_nome, t.endereco, t.telefone
                FROM consulta c
                JOIN animal a ON a.id = c.id_animal
                JOIN tutor  t ON t.id = a.id_tutor
                ORDER BY c.data DESC
                """;
        List<Consulta> lista = new ArrayList<>();
        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar consultas: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<Consulta> listarPorAnimal(int idAnimal) {
        String sql = """
                SELECT c.id, c.data, c.motivo, c.valor,
                       a.id AS animal_id, a.nome AS animal_nome, a.especie, a.raca,
                       t.id AS tutor_id, t.nome AS tutor_nome, t.endereco, t.telefone
                FROM consulta c
                JOIN animal a ON a.id = c.id_animal
                JOIN tutor  t ON t.id = a.id_tutor
                WHERE c.id_animal = ?
                ORDER BY c.data DESC
                """;
        List<Consulta> lista = new ArrayList<>();
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar consultas por animal: " + e.getMessage(), e);
        }
        return lista;
    }

    public void atualizar(Consulta consulta) {
        String sql = "UPDATE consulta SET id_animal = ?, data = ?, motivo = ?, valor = ? WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, consulta.getAnimal().getId());
            stmt.setDate(2, Date.valueOf(consulta.getData()));
            stmt.setString(3, consulta.getMotivo());
            stmt.setBigDecimal(4, consulta.getValor());
            stmt.setInt(5, consulta.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar consulta: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM consulta WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar consulta: " + e.getMessage(), e);
        }
    }

    private Consulta mapear(ResultSet rs) throws SQLException {
        Tutor tutor = new Tutor();
        tutor.setId(rs.getInt("tutor_id"));
        tutor.setNome(rs.getString("tutor_nome"));
        tutor.setEndereco(rs.getString("endereco"));
        tutor.setTelefone(rs.getString("telefone"));

        Animal animal = new Animal();
        animal.setId(rs.getInt("animal_id"));
        animal.setNome(rs.getString("animal_nome"));
        animal.setEspecie(rs.getString("especie"));
        animal.setRaca(rs.getString("raca"));
        animal.setTutor(tutor);

        Consulta c = new Consulta();
        c.setId(rs.getInt("id"));
        c.setAnimal(animal);
        c.setData(rs.getDate("data").toLocalDate());
        c.setMotivo(rs.getString("motivo"));
        c.setValor(rs.getBigDecimal("valor"));
        return c;
    }
}
