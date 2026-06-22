package br.edu.faculdade.oficina.controller;

import br.edu.faculdade.oficina.model.Cliente;
import br.edu.faculdade.oficina.service.ClienteService;

import java.util.List;

public class ClienteController {
    private final ClienteService service = new ClienteService();

    public Cliente cadastrar(String nome, String telefone) {
        Cliente cliente = new Cliente(nome, telefone);
        Cliente salvo = service.cadastrar(cliente);
        System.out.println("[Cliente cadastrado] " + salvo);
        return salvo;
    }

    public Cliente buscar(int id) {
        Cliente cliente = service.buscarPorId(id);
        System.out.println("[Cliente encontrado] " + cliente);
        return cliente;
    }

    public List<Cliente> listarTodos() {
        List<Cliente> lista = service.listarTodos();
        System.out.println("[Lista de clientes]");
        lista.forEach(System.out::println);
        return lista;
    }

    public void atualizar(Cliente cliente) {
        service.atualizar(cliente);
        System.out.println("[Cliente atualizado] " + cliente);
    }

    public void deletar(int id) {
        service.deletar(id);
        System.out.println("[Cliente removido] id=" + id);
    }
}
