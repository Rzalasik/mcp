package br.edu.faculdade.escola.controller;

import br.edu.faculdade.escola.model.Aluno;
import br.edu.faculdade.escola.service.AlunoService;

import java.util.List;

public class AlunoController {
    private final AlunoService service = new AlunoService();

    public Aluno cadastrar(String nome, String email, String telefone) {
        Aluno aluno = new Aluno(nome, email, telefone);
        Aluno salvo = service.cadastrar(aluno);
        System.out.println("[Aluno cadastrado] " + salvo);
        return salvo;
    }

    public Aluno buscar(int id) {
        Aluno aluno = service.buscarPorId(id);
        System.out.println("[Aluno encontrado] " + aluno);
        return aluno;
    }

    public List<Aluno> listarTodos() {
        List<Aluno> lista = service.listarTodos();
        System.out.println("[Lista de alunos]");
        lista.forEach(System.out::println);
        return lista;
    }

    public void atualizar(Aluno aluno) {
        service.atualizar(aluno);
        System.out.println("[Aluno atualizado] " + aluno);
    }

    public void deletar(int id) {
        service.deletar(id);
        System.out.println("[Aluno removido] id=" + id);
    }
}
