package br.edu.faculdade.clinicaveterinaria.controller;

import br.edu.faculdade.clinicaveterinaria.model.Animal;
import br.edu.faculdade.clinicaveterinaria.service.AnimalService;

import java.util.List;

public class AnimalController {
    private final AnimalService service = new AnimalService();

    public Animal cadastrar(String nome, String especie, String raca, int idTutor) {
        Animal animal = new Animal(nome, especie, raca, idTutor);
        Animal salvo = service.cadastrar(animal);
        System.out.println("[Animal cadastrado] " + salvo);
        return salvo;
    }

    public Animal buscar(int id) {
        Animal animal = service.buscarPorId(id);
        System.out.println("[Animal encontrado] " + animal);
        return animal;
    }

    public List<Animal> listarTodos() {
        List<Animal> lista = service.listarTodos();
        System.out.println("[Lista de animais]");
        lista.forEach(System.out::println);
        return lista;
    }

    public List<Animal> listarPorTutor(int idTutor) {
        List<Animal> lista = service.listarPorTutor(idTutor);
        System.out.println("[Animais do tutor id=" + idTutor + "]");
        lista.forEach(System.out::println);
        return lista;
    }

    public void atualizar(Animal animal) {
        service.atualizar(animal);
        System.out.println("[Animal atualizado] " + animal);
    }

    public void deletar(int id) {
        service.deletar(id);
        System.out.println("[Animal removido] id=" + id);
    }
}
