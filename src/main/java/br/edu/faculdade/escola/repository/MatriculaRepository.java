package br.edu.faculdade.escola.repository;

import br.edu.faculdade.escola.model.Aluno;
import br.edu.faculdade.escola.model.Curso;
import br.edu.faculdade.escola.model.Matricula;
import br.edu.faculdade.escola.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatriculaRepository {

    public Matricula registrarComTransacao(Matricula matricula) {
        Connection conn = null;
        try {
            conn = Conexao.obterConexao();
            conn.setAutoCommit(false);

            String sqlInsert = """
                    INSERT INTO matricula (id_aluno, id_curso, data_matricula, valor)
                    VALUES (?, ?, ?, ?) RETURNING id
                    """;
            try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                stmtInsert.setInt(1, matricula.getAluno().getId());
                stmtInsert.setInt(2, matricula.getCurso().getId());
                stmtInsert.setDate(3, Date.valueOf(matricula.getDataMatricula()));
                stmtInsert.setBigDecimal(4, matricula.getValor());
                ResultSet rs = stmtInsert.executeQuery();
                if (rs.next()) matricula.setId(rs.getInt("id"));
            }

            String sqlUpdate = "UPDATE curso SET vagas_disponiveis = vagas_disponiveis - 1 WHERE id = ?";
            try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
                stmtUpdate.setInt(1, matricula.getCurso().getId());
                stmtUpdate.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignored */ }
            }
            throw new RuntimeException("Erro ao registrar matrícula: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { /* ignored */ }
            }
        }
        return matricula;
    }

    public Optional<Matricula> buscarPorId(int id) {
        String sql = """
                SELECT m.id, m.data_matricula, m.valor,
                       a.id AS aluno_id, a.nome AS aluno_nome, a.email, a.telefone,
                       c.id AS curso_id, c.nome AS curso_nome, c.descricao,
                       c.carga_horaria, c.vagas_totais, c.vagas_disponiveis
                FROM matricula m
                JOIN aluno a ON a.id = m.id_aluno
                JOIN curso  c ON c.id = m.id_curso
                WHERE m.id = ?
                """;
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
        String sql = """
                SELECT m.id, m.data_matricula, m.valor,
                       a.id AS aluno_id, a.nome AS aluno_nome, a.email, a.telefone,
                       c.id AS curso_id, c.nome AS curso_nome, c.descricao,
                       c.carga_horaria, c.vagas_totais, c.vagas_disponiveis
                FROM matricula m
                JOIN aluno a ON a.id = m.id_aluno
                JOIN curso  c ON c.id = m.id_curso
                ORDER BY m.id
                """;
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
        String sql = """
                SELECT m.id, m.data_matricula, m.valor,
                       a.id AS aluno_id, a.nome AS aluno_nome, a.email, a.telefone,
                       c.id AS curso_id, c.nome AS curso_nome, c.descricao,
                       c.carga_horaria, c.vagas_totais, c.vagas_disponiveis
                FROM matricula m
                JOIN aluno a ON a.id = m.id_aluno
                JOIN curso  c ON c.id = m.id_curso
                WHERE m.id_aluno = ?
                ORDER BY m.data_matricula DESC
                """;
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
        String sql = """
                SELECT m.id, m.data_matricula, m.valor,
                       a.id AS aluno_id, a.nome AS aluno_nome, a.email, a.telefone,
                       c.id AS curso_id, c.nome AS curso_nome, c.descricao,
                       c.carga_horaria, c.vagas_totais, c.vagas_disponiveis
                FROM matricula m
                JOIN aluno a ON a.id = m.id_aluno
                JOIN curso  c ON c.id = m.id_curso
                WHERE m.id_curso = ?
                ORDER BY m.data_matricula DESC
                """;
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
        Aluno aluno = new Aluno();
        aluno.setId(rs.getInt("aluno_id"));
        aluno.setNome(rs.getString("aluno_nome"));
        aluno.setEmail(rs.getString("email"));
        aluno.setTelefone(rs.getString("telefone"));

        Curso curso = new Curso();
        curso.setId(rs.getInt("curso_id"));
        curso.setNome(rs.getString("curso_nome"));
        curso.setDescricao(rs.getString("descricao"));
        curso.setCargaHoraria(rs.getInt("carga_horaria"));
        curso.setVagasTotais(rs.getInt("vagas_totais"));
        curso.setVagasDisponiveis(rs.getInt("vagas_disponiveis"));

        Matricula m = new Matricula();
        m.setId(rs.getInt("id"));
        m.setAluno(aluno);
        m.setCurso(curso);
        m.setDataMatricula(rs.getDate("data_matricula").toLocalDate());
        m.setValor(rs.getBigDecimal("valor"));
        return m;
    }
}
