package br.edu.faculdade.oficina.repository;

import br.edu.faculdade.oficina.model.OrdemServico;
import br.edu.faculdade.oficina.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdemServicoRepository {

    public OrdemServico salvar(OrdemServico os) {
        String sql = "INSERT INTO ordem_servico (id_veiculo, descricao, valor, status) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, os.getIdVeiculo());
            stmt.setString(2, os.getDescricao());
            stmt.setBigDecimal(3, os.getValor());
            stmt.setString(4, os.getStatus());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) os.setId(rs.getInt("id"));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar ordem de serviço: " + e.getMessage(), e);
        }
        return os;
    }

    public Optional<OrdemServico> buscarPorId(int id) {
        String sql = "SELECT * FROM ordem_servico WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ordem de serviço: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public List<OrdemServico> listarTodos() {
        String sql = "SELECT * FROM ordem_servico";
        List<OrdemServico> lista = new ArrayList<>();
        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar ordens de serviço: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<OrdemServico> listarPorVeiculo(int idVeiculo) {
        String sql = "SELECT * FROM ordem_servico WHERE id_veiculo = ?";
        List<OrdemServico> lista = new ArrayList<>();
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idVeiculo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar ordens por veiculo: " + e.getMessage(), e);
        }
        return lista;
    }

    public void atualizar(OrdemServico os) {
        String sql = "UPDATE ordem_servico SET id_veiculo = ?, descricao = ?, valor = ?, status = ? WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, os.getIdVeiculo());
            stmt.setString(2, os.getDescricao());
            stmt.setBigDecimal(3, os.getValor());
            stmt.setString(4, os.getStatus());
            stmt.setInt(5, os.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar ordem de serviço: " + e.getMessage(), e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM ordem_servico WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar ordem de serviço: " + e.getMessage(), e);
        }
    }

    private OrdemServico mapear(ResultSet rs) throws SQLException {
        OrdemServico os = new OrdemServico();
        os.setId(rs.getInt("id"));
        os.setIdVeiculo(rs.getInt("id_veiculo"));
        os.setDescricao(rs.getString("descricao"));
        os.setValor(rs.getBigDecimal("valor"));
        os.setStatus(rs.getString("status"));
        return os;
    }
}
