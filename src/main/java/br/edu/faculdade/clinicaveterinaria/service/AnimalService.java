package br.edu.faculdade.clinicaveterinaria.service;

import br.edu.faculdade.clinicaveterinaria.model.Animal;
import br.edu.faculdade.clinicaveterinaria.model.Tutor;
import br.edu.faculdade.clinicaveterinaria.repository.AnimalRepository;
import br.edu.faculdade.clinicaveterinaria.repository.TutorRepository;

import java.util.List;

public class AnimalService {
    private final AnimalRepository repository = new AnimalRepository();
    private final TutorRepository tutorRepository = new TutorRepository();

    public Animal cadastrar(String nome, String especie, String raca, int idTutor) {
        Tutor tutor = tutorRepository.buscarPorId(idTutor)
                .orElseThrow(() -> new IllegalArgumentException("Tutor não encontrado com id " + idTutor));
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome do animal é obrigatório.");
        return repository.salvar(new Animal(nome, especie, raca, tutor));
    }

    public Animal buscarPorId(int id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Animal não encontrado com id " + id));
    }

    public List<Animal> listarTodos() {
        return repository.listarTodos();
    }

    public List<Animal> listarPorTutor(int idTutor) {
        return repository.listarPorTutor(idTutor);
    }

    public void atualizar(Animal animal) {
        repository.atualizar(animal);
    }

    public void deletar(int id) {
        repository.deletar(id);
    }
}
