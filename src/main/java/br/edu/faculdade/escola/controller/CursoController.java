package br.edu.faculdade.escola.controller;

import br.edu.faculdade.escola.model.Curso;
import br.edu.faculdade.escola.service.CursoService;

import java.util.List;

public class CursoController {
    private final CursoService service = new CursoService();

    public Curso cadastrar(String nome, String descricao, int cargaHoraria, int vagasTotais) {
        Curso curso = new Curso(nome, descricao, cargaHoraria, vagasTotais);
        Curso salvo = service.cadastrar(curso);
        System.out.println("[Curso cadastrado] " + salvo);
        return salvo;
    }

    public Curso buscar(int id) {
        Curso curso = service.buscarPorId(id);
        System.out.println("[Curso encontrado] " + curso);
        return curso;
    }

    public List<Curso> listarTodos() {
        List<Curso> lista = service.listarTodos();
        System.out.println("[Lista de cursos]");
        lista.forEach(System.out::println);
        return lista;
    }

    public void atualizar(Curso curso) {
        service.atualizar(curso);
        System.out.println("[Curso atualizado] " + curso);
    }

    public void deletar(int id) {
        service.deletar(id);
        System.out.println("[Curso removido] id=" + id);
    }
}
