package br.edu.faculdade.clinicaveterinaria;

import br.edu.faculdade.clinicaveterinaria.controller.AnimalController;
import br.edu.faculdade.clinicaveterinaria.controller.ConsultaController;
import br.edu.faculdade.clinicaveterinaria.controller.TutorController;
import br.edu.faculdade.clinicaveterinaria.model.Animal;
import br.edu.faculdade.clinicaveterinaria.model.Tutor;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        TutorController tutorController = new TutorController();
        AnimalController animalController = new AnimalController();
        ConsultaController consultaController = new ConsultaController();

        System.out.println("=== SISTEMA DE CLÍNICA VETERINÁRIA ===\n");

        // 1. Cadastro do tutor
        System.out.println("--- Cadastrando tutor ---");
        Tutor tutor = tutorController.cadastrar("Maria Silva", "Rua das Flores, 123", "11 91234-5678");

        // 2. Cadastro de animais vinculados ao tutor
        System.out.println("\n--- Cadastrando animais ---");
        Animal rex = animalController.cadastrar("Rex", "Cão", "Labrador", tutor.getId());
        Animal mimi = animalController.cadastrar("Mimi", "Gato", "Siamês", tutor.getId());

        // 3. Registrar consultas para os animais (movimento)
        System.out.println("\n--- Registrando consultas ---");
        consultaController.registrar(rex.getId(), LocalDate.now(), "Vacinação anual", new BigDecimal("150.00"));
        consultaController.registrar(rex.getId(), LocalDate.now().minusDays(30), "Check-up geral", new BigDecimal("200.00"));
        consultaController.registrar(mimi.getId(), LocalDate.now(), "Castração", new BigDecimal("350.00"));

        // 4. Histórico de consultas por animal
        System.out.println("\n--- Histórico de Rex ---");
        consultaController.listarPorAnimal(rex.getId());

        // 5. Todos os animais do tutor
        System.out.println("\n--- Animais de " + tutor.getNome() + " ---");
        animalController.listarPorTutor(tutor.getId());

        // 6. Teste de regra de negócio: consulta com valor negativo
        System.out.println("\n--- Testando regra: valor negativo ---");
        try {
            consultaController.registrar(rex.getId(), LocalDate.now(), "Teste inválido", new BigDecimal("-50.00"));
        } catch (IllegalArgumentException e) {
            System.out.println("[ERRO esperado] " + e.getMessage());
        }

        // 7. Teste de regra de negócio: animal inexistente
        System.out.println("\n--- Testando regra: animal inexistente ---");
        try {
            consultaController.registrar(9999, LocalDate.now(), "Teste inválido", new BigDecimal("100.00"));
        } catch (IllegalArgumentException e) {
            System.out.println("[ERRO esperado] " + e.getMessage());
        }
    }
}
