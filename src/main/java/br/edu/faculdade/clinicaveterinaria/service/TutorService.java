package br.edu.faculdade.clinicaveterinaria.service;

import br.edu.faculdade.clinicaveterinaria.model.Tutor;
import br.edu.faculdade.clinicaveterinaria.repository.TutorRepository;

import java.util.List;

public class TutorService {
    private final TutorRepository repository = new TutorRepository();

    public Tutor cadastrar(Tutor tutor) {
        if (tutor.getNome() == null || tutor.getNome().isBlank())
            throw new IllegalArgumentException("Nome do tutor é obrigatório.");
        if (tutor.getTelefone() == null || tutor.getTelefone().isBlank())
            throw new IllegalArgumentException("Telefone do tutor é obrigatório.");
        return repository.salvar(tutor);
    }

    public Tutor buscarPorId(int id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado com id " + id));
    }

    public List<Tutor> listarTodos() {
        return repository.listarTodos();
    }

    public void atualizar(Tutor tutor) {
        repository.atualizar(tutor);
    }

    public void deletar(int id) {
        repository.deletar(id);
    }
}
