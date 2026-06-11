package br.edu.faculdade.clinicaveterinaria.repository;

import br.edu.faculdade.clinicaveterinaria.model.Tutor;
import br.edu.faculdade.clinicaveterinaria.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TutorRepository {

    public Tutor salvar(Tutor tutor) {
        String sql = "INSERT INTO tutor (nome, endereco, telefone) VALUES (?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tutor.getNome());
            stmt.setString(2, tutor.getEndereco());
            stmt.setString(3, tutor.getTelefone());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) tutor.setId(rs.getInt("id"));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar tutor: " + e.getMessage(), e);
        }
        return tutor;
    }

    public Optional<Tutor> buscarPorId(int id) {
        String sql = "SELECT * FROM tutor WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar tutor: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public List<Tutor> listarTodos() {
        String sql = "SELECT * FROM tutor";
        List<Tutor> lista = new ArrayList<>();
        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar tutores: " + e.getMessage(), e);
        }
        return lista;
    }

    public void atualizar(Tutor tutor) {
        String sql = "UPDATE tutor SET nome = ?, endereco = ?, telefone = ? WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tutor.getNome());
            stmt.setString(2, tutor.getEndereco());
            stmt.setString(3, tutor.getTelefone());
            stmt.setInt(4, tutor.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar tutor: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM tutor WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar tutor: " + e.getMessage(), e);
        }
    }

    private Tutor mapear(ResultSet rs) throws SQLException {
        Tutor t = new Tutor();
        t.setId(rs.getInt("id"));
        t.setNome(rs.getString("nome"));
        t.setEndereco(rs.getString("endereco"));
        t.setTelefone(rs.getString("telefone"));
        return t;
    }
}
