package br.edu.faculdade.oficina.service;

import br.edu.faculdade.oficina.model.Cliente;
import br.edu.faculdade.oficina.repository.ClienteRepository;

import java.util.List;

public class ClienteService {
    private final ClienteRepository repository = new ClienteRepository();

    public Cliente cadastrar(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().isBlank())
            throw new IllegalArgumentException("Nome do cliente é obrigatório.");
        if (cliente.getTelefone() == null || cliente.getTelefone().isBlank())
            throw new IllegalArgumentException("Telefone do cliente é obrigatório.");
        return repository.salvar(cliente);
    }

    public Cliente buscarPorId(int id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id " + id));
    }

    public List<Cliente> listarTodos() {
        return repository.listarTodos();
    }

    public void atualizar(Cliente cliente) {
        repository.atualizar(cliente);
    }

    public void deletar(int id) {
        repository.deletar(id);
    }
}
