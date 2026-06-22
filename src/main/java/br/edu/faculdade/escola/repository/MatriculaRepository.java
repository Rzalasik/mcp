package br.edu.faculdade.escola.repository;

import br.edu.faculdade.escola.model.Matricula;
import br.edu.faculdade.escola.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatriculaRepository {

    public Matricula salvar(Matricula matricula) {
        String sql = "INSERT INTO matricula (id_aluno, id_curso, data_matricula, valor) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matricula.getIdAluno());
            stmt.setInt(2, matricula.getIdCurso());
            stmt.setDate(3, Date.valueOf(matricula.getDataMatricula()));
            stmt.setBigDecimal(4, matricula.getValor());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) matricula.setId(rs.getInt("id"));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar matrícula: " + e.getMessage(), e);
        }
        return matricula;
    }

    public Optional<Matricula> buscarPorId(int id) {
        String sql = "SELECT * FROM matricula WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return Optional.of(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar matrícula: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public boolean existeMatricula(int idAluno, int idCurso) {
        String sql = "SELECT 1 FROM matricula WHERE id_aluno = ? AND id_curso = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            stmt.setInt(2, idCurso);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar matrícula duplicada: " + e.getMessage(), e);
        }
    }

    public List<Matricula> listarTodos() {
        String sql = "SELECT * FROM matricula";
        List<Matricula> lista = new ArrayList<>();
        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar matrículas: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<Matricula> listarPorAluno(int idAluno) {
        String sql = "SELECT * FROM matricula WHERE id_aluno = ?";
        List<Matricula> lista = new ArrayList<>();
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar matrículas por aluno: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<Matricula> listarPorCurso(int idCurso) {
        String sql = "SELECT * FROM matricula WHERE id_curso = ?";
        List<Matricula> lista = new ArrayList<>();
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar matrículas por curso: " + e.getMessage(), e);
        }
        return lista;
    }

    public void deletar(int id) {
        String sql = "DELETE FROM matricula WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar matrícula: " + e.getMessage(), e);
        }
    }

    private Matricula mapear(ResultSet rs) throws SQLException {
        Matricula m = new Matricula();
        m.setId(rs.getInt("id"));
        m.setIdAluno(rs.getInt("id_aluno"));
        m.setIdCurso(rs.getInt("id_curso"));
        m.setDataMatricula(rs.getDate("data_matricula").toLocalDate());
        m.setValor(rs.getBigDecimal("valor"));
        return m;
    }
}
