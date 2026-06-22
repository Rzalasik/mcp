package br.edu.faculdade.clinicaveterinaria.service;

import br.edu.faculdade.clinicaveterinaria.model.Animal;
import br.edu.faculdade.clinicaveterinaria.model.Consulta;
import br.edu.faculdade.clinicaveterinaria.repository.AnimalRepository;
import br.edu.faculdade.clinicaveterinaria.repository.ConsultaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ConsultaService {
    private final ConsultaRepository repository = new ConsultaRepository();
    private final AnimalRepository animalRepository = new AnimalRepository();

    public Consulta registrar(int idAnimal, LocalDate data, String motivo, BigDecimal valor) {
        Animal animal = animalRepository.buscarPorId(idAnimal)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Não é possível registrar consulta: animal de id " + idAnimal + " não está cadastrado."));
        Consulta consulta = new Consulta(animal, data, motivo, valor);
        if (!consulta.isValorValido())
            throw new IllegalArgumentException("O valor da consulta não pode ser negativo.");
        if (data == null)
            throw new IllegalArgumentException("A data da consulta é obrigatória.");
        return repository.salvar(consulta);
    }

    public Consulta buscarPorId(int id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada com id " + id));
    }

    public List<Consulta> listarTodos() {
        return repository.listarTodos();
    }

    public List<Consulta> listarPorAnimal(int idAnimal) {
        return repository.listarPorAnimal(idAnimal);
    }

    public void atualizar(Consulta consulta) {
        repository.atualizar(consulta);
    }

    public void deletar(int id) {
        repository.deletar(id);
    }
}
