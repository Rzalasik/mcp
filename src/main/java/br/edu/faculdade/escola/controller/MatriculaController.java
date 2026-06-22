package br.edu.faculdade.escola.controller;

import br.edu.faculdade.escola.model.Matricula;
import br.edu.faculdade.escola.service.MatriculaService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class MatriculaController {
    private final MatriculaService service = new MatriculaService();

    public Matricula matricular(int idAluno, int idCurso, BigDecimal valor) {
        Matricula m = new Matricula(idAluno, idCurso, LocalDate.now(), valor);
        Matricula salva = service.matricular(m);
        System.out.println("[Matrícula realizada] " + salva);
        return salva;
    }

    public List<Matricula> listarPorAluno(int idAluno) {
        List<Matricula> lista = service.listarPorAluno(idAluno);
        System.out.println("[Cursos do aluno id=" + idAluno + "]");
        lista.forEach(System.out::println);
        return lista;
    }

    public List<Matricula> listarPorCurso(int idCurso) {
        List<Matricula> lista = service.listarPorCurso(idCurso);
        System.out.println("[Alunos do curso id=" + idCurso + "]");
        lista.forEach(System.out::println);
        return lista;
    }

    public List<Matricula> listarTodos() {
        List<Matricula> lista = service.listarTodos();
        System.out.println("[Lista de matrículas]");
        lista.forEach(System.out::println);
        return lista;
    }

    public void deletar(int id) {
        service.deletar(id);
        System.out.println("[Matrícula removida] id=" + id);
    }
}
