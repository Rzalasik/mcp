package br.edu.faculdade.escola.service;

import br.edu.faculdade.escola.model.Aluno;
import br.edu.faculdade.escola.model.Curso;
import br.edu.faculdade.escola.model.Matricula;
import br.edu.faculdade.escola.repository.AlunoRepository;
import br.edu.faculdade.escola.repository.CursoRepository;
import br.edu.faculdade.escola.repository.MatriculaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class MatriculaService {
    private final MatriculaRepository repository = new MatriculaRepository();
    private final AlunoRepository alunoRepository = new AlunoRepository();
    private final CursoRepository cursoRepository = new CursoRepository();

    public Matricula matricular(int idAluno, int idCurso, BigDecimal valor) {
        Aluno aluno = alunoRepository.buscarPorId(idAluno)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Aluno de id " + idAluno + " não está cadastrado."));

        Curso curso = cursoRepository.buscarPorId(idCurso)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Curso de id " + idCurso + " não está cadastrado."));

        if (!curso.temVagasDisponiveis())
            throw new IllegalArgumentException("Curso '" + curso.getNome() + "' não possui vagas disponíveis.");

        if (repository.existeMatricula(idAluno, idCurso))
            throw new IllegalArgumentException("Aluno já está matriculado neste curso.");

        if (valor == null || valor.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("O valor da matrícula não pode ser negativo.");

        Matricula matricula = new Matricula(aluno, curso, LocalDate.now(), valor);
        return repository.registrarComTransacao(matricula);
    }

    public Matricula buscarPorId(int id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada com id " + id));
    }

    public List<Matricula> listarTodos() {
        return repository.listarTodos();
    }

    public List<Matricula> listarPorAluno(int idAluno) {
        return repository.listarPorAluno(idAluno);
    }

    public List<Matricula> listarPorCurso(int idCurso) {
        return repository.listarPorCurso(idCurso);
    }

    public void deletar(int id) {
        repository.deletar(id);
    }
}
