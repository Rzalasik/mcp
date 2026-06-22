package br.edu.faculdade.oficina.repository;

import br.edu.faculdade.oficina.model.Veiculo;
import br.edu.faculdade.oficina.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VeiculoRepository {

    public Veiculo salvar(Veiculo veiculo) {
        String sql = "INSERT INTO veiculo (placa, modelo, ano, id_cliente) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setInt(3, veiculo.getAno());
            stmt.setInt(4, veiculo.getIdCliente());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) veiculo.setId(rs.getInt("id"));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar veiculo: " + e.getMessage(), e);
        }
        return veiculo;
    }

    public Optional<Veiculo> buscarPorId(int id) {
        String sql = "SELECT * FROM veiculo WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar veiculo: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public List<Veiculo> listarTodos() {
        String sql = "SELECT * FROM veiculo";
        List<Veiculo> lista = new ArrayList<>();
        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar veiculos: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<Veiculo> listarPorCliente(int idCliente) {
        String sql = "SELECT * FROM veiculo WHERE id_cliente = ?";
        List<Veiculo> lista = new ArrayList<>();
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar veiculos por cliente: " + e.getMessage(), e);
        }
        return lista;
    }

    public void atualizar(Veiculo veiculo) {
        String sql = "UPDATE veiculo SET placa = ?, modelo = ?, ano = ?, id_cliente = ? WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setInt(3, veiculo.getAno());
            stmt.setInt(4, veiculo.getIdCliente());
            stmt.setInt(5, veiculo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar veiculo: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM veiculo WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar veiculo: " + e.getMessage(), e);
        }
    }

    private Veiculo mapear(ResultSet rs) throws SQLException {
        Veiculo v = new Veiculo();
        v.setId(rs.getInt("id"));
        v.setPlaca(rs.getString("placa"));
        v.setModelo(rs.getString("modelo"));
        v.setAno(rs.getInt("ano"));
        v.setIdCliente(rs.getInt("id_cliente"));
        return v;
    }
}
