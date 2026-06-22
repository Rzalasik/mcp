package br.edu.faculdade.escola.service;

import br.edu.faculdade.escola.model.Curso;
import br.edu.faculdade.escola.repository.CursoRepository;

import java.util.List;

public class CursoService {
    private final CursoRepository repository = new CursoRepository();

    public Curso cadastrar(Curso curso) {
        if (curso.getNome() == null || curso.getNome().isBlank())
            throw new IllegalArgumentException("Nome do curso é obrigatório.");
        if (curso.getVagasTotais() <= 0)
            throw new IllegalArgumentException("O número de vagas deve ser maior que zero.");
        curso.setVagasDisponiveis(curso.getVagasTotais());
        return repository.salvar(curso);
    }

    public Curso buscarPorId(int id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com id " + id));
    }

    public List<Curso> listarTodos() {
        return repository.listarTodos();
    }

    public void atualizar(Curso curso) {
        repository.atualizar(curso);
    }

    public void deletar(int id) {
        repository.deletar(id);
    }
}
