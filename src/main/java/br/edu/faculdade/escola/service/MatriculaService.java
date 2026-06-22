package br.edu.faculdade.escola.service;

import br.edu.faculdade.escola.model.Curso;
import br.edu.faculdade.escola.model.Matricula;
import br.edu.faculdade.escola.repository.AlunoRepository;
import br.edu.faculdade.escola.repository.CursoRepository;
import br.edu.faculdade.escola.repository.MatriculaRepository;

import java.math.BigDecimal;
import java.util.List;

public class MatriculaService {
    private final MatriculaRepository repository = new MatriculaRepository();
    private final AlunoRepository alunoRepository = new AlunoRepository();
    private final CursoRepository cursoRepository = new CursoRepository();

    public Matricula matricular(Matricula matricula) {
        alunoRepository.buscarPorId(matricula.getIdAluno())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Aluno de id " + matricula.getIdAluno() + " não está cadastrado."));

        Curso curso = cursoRepository.buscarPorId(matricula.getIdCurso())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Curso de id " + matricula.getIdCurso() + " não está cadastrado."));

        if (curso.getVagasDisponiveis() <= 0)
            throw new IllegalArgumentException("Curso '" + curso.getNome() + "' não possui vagas disponíveis.");

        if (repository.existeMatricula(matricula.getIdAluno(), matricula.getIdCurso()))
            throw new IllegalArgumentException("Aluno já está matriculado neste curso.");

        if (matricula.getValor() == null || matricula.getValor().compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("O valor da matrícula não pode ser negativo.");

        Matricula salva = repository.salvar(matricula);

        curso.setVagasDisponiveis(curso.getVagasDisponiveis() - 1);
        cursoRepository.atualizar(curso);

        return salva;
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
