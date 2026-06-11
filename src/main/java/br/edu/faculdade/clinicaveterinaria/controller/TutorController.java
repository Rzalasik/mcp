package br.edu.faculdade.clinicaveterinaria.controller;

import br.edu.faculdade.clinicaveterinaria.model.Tutor;
import br.edu.faculdade.clinicaveterinaria.service.TutorService;

import java.util.List;

public class TutorController {
    private final TutorService service = new TutorService();

    public Tutor cadastrar(String nome, String endereco, String telefone) {
        Tutor tutor = new Tutor(nome, endereco, telefone);
        Tutor salvo = service.cadastrar(tutor);
        System.out.println("[Tutor cadastrado] " + salvo);
        return salvo;
    }

    public Tutor buscar(int id) {
        Tutor tutor = service.buscarPorId(id);
        System.out.println("[Tutor encontrado] " + tutor);
        return tutor;
    }

    public List<Tutor> listarTodos() {
        List<Tutor> lista = service.listarTodos();
        System.out.println("[Lista de tutores]");
        lista.forEach(System.out::println);
        return lista;
    }

    public void atualizar(Tutor tutor) {
        service.atualizar(tutor);
        System.out.println("[Tutor atualizado] " + tutor);
    }

    public void deletar(int id) {
        service.deletar(id);
        System.out.println("[Tutor removido] id=" + id);
    }
}
