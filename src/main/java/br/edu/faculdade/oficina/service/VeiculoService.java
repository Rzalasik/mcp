package br.edu.faculdade.oficina.service;

import br.edu.faculdade.oficina.model.Veiculo;
import br.edu.faculdade.oficina.repository.ClienteRepository;
import br.edu.faculdade.oficina.repository.VeiculoRepository;

import java.util.List;

public class VeiculoService {
    private final VeiculoRepository repository = new VeiculoRepository();
    private final ClienteRepository clienteRepository = new ClienteRepository();

    public Veiculo cadastrar(Veiculo veiculo) {
        clienteRepository.buscarPorId(veiculo.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com id " + veiculo.getIdCliente()));
        if (veiculo.getPlaca() == null || veiculo.getPlaca().isBlank())
            throw new IllegalArgumentException("Placa do veículo é obrigatória.");
        return repository.salvar(veiculo);
    }

    public Veiculo buscarPorId(int id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado com id " + id));
    }

    public List<Veiculo> listarTodos() {
        return repository.listarTodos();
    }

    public List<Veiculo> listarPorCliente(int idCliente) {
        return repository.listarPorCliente(idCliente);
    }

    public void atualizar(Veiculo veiculo) {
        repository.atualizar(veiculo);
    }

    public void deletar(int id) {
        repository.deletar(id);
    }
}
