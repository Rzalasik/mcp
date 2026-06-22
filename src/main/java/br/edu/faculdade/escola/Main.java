package br.edu.faculdade.escola;

import br.edu.faculdade.escola.controller.AlunoController;
import br.edu.faculdade.escola.controller.CursoController;
import br.edu.faculdade.escola.controller.MatriculaController;
import br.edu.faculdade.escola.model.Aluno;
import br.edu.faculdade.escola.model.Curso;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        AlunoController alunoController = new AlunoController();
        CursoController cursoController = new CursoController();
        MatriculaController matriculaController = new MatriculaController();

        System.out.println("=== SISTEMA DE ESCOLA DE CURSOS LIVRES ===\n");

        // 1. Cadastro de alunos
        System.out.println("--- Cadastrando alunos ---");
        Aluno ana = alunoController.cadastrar("Ana Costa", "ana@email.com", "11 91111-2222");
        Aluno bruno = alunoController.cadastrar("Bruno Lima", "bruno@email.com", "11 93333-4444");
        Aluno carla = alunoController.cadastrar("Carla Souza", "carla@email.com", "11 95555-6666");

        // 2. Cadastro de cursos (com limite de vagas)
        System.out.println("\n--- Cadastrando cursos ---");
        Curso java = cursoController.cadastrar("Java Básico", "Introdução à linguagem Java", 40, 2);
        Curso python = cursoController.cadastrar("Python para Dados", "Python aplicado à ciência de dados", 60, 10);

        // 3. Realizar matrículas (movimento)
        System.out.println("\n--- Realizando matrículas ---");
        matriculaController.matricular(ana.getId(), java.getId(), new BigDecimal("299.90"));
        matriculaController.matricular(bruno.getId(), java.getId(), new BigDecimal("299.90"));
        matriculaController.matricular(ana.getId(), python.getId(), new BigDecimal("399.90"));

        // 4. Listar alunos de um curso
        System.out.println("\n--- Alunos matriculados em Java Básico ---");
        matriculaController.listarPorCurso(java.getId());

        // 5. Listar cursos de um aluno
        System.out.println("\n--- Cursos da Ana ---");
        matriculaController.listarPorAluno(ana.getId());

        // 6. Teste de regra: curso sem vagas (Java já tem 2/2)
        System.out.println("\n--- Testando regra: curso sem vagas ---");
        try {
            matriculaController.matricular(carla.getId(), java.getId(), new BigDecimal("299.90"));
        } catch (IllegalArgumentException e) {
            System.out.println("[ERRO esperado] " + e.getMessage());
        }

        // 7. Teste de regra: matrícula duplicada
        System.out.println("\n--- Testando regra: matrícula duplicada ---");
        try {
            matriculaController.matricular(ana.getId(), python.getId(), new BigDecimal("399.90"));
        } catch (IllegalArgumentException e) {
            System.out.println("[ERRO esperado] " + e.getMessage());
        }

        // 8. Teste de regra: valor negativo
        System.out.println("\n--- Testando regra: valor negativo ---");
        try {
            matriculaController.matricular(carla.getId(), python.getId(), new BigDecimal("-50.00"));
        } catch (IllegalArgumentException e) {
            System.out.println("[ERRO esperado] " + e.getMessage());
        }
    }
}
