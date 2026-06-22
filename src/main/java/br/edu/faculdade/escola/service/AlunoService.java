package br.edu.faculdade.escola.service;

import br.edu.faculdade.escola.model.Aluno;
import br.edu.faculdade.escola.repository.AlunoRepository;

import java.util.List;

public class AlunoService {
    private final AlunoRepository repository = new AlunoRepository();

    public Aluno cadastrar(Aluno aluno) {
        if (aluno.getNome() == null || aluno.getNome().isBlank())
            throw new IllegalArgumentException("Nome do aluno é obrigatório.");
        if (aluno.getEmail() == null || aluno.getEmail().isBlank())
            throw new IllegalArgumentException("E-mail do aluno é obrigatório.");
        return repository.salvar(aluno);
    }

    public Aluno buscarPorId(int id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id " + id));
    }

    public List<Aluno> listarTodos() {
        return repository.listarTodos();
    }

    public void atualizar(Aluno aluno) {
        repository.atualizar(aluno);
    }

    public void deletar(int id) {
        repository.deletar(id);
    }
}
