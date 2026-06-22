package br.edu.faculdade.oficina.service;

import br.edu.faculdade.oficina.model.OrdemServico;
import br.edu.faculdade.oficina.repository.OrdemServicoRepository;
import br.edu.faculdade.oficina.repository.VeiculoRepository;

import java.math.BigDecimal;
import java.util.List;

public class OrdemServicoService {
    private final OrdemServicoRepository repository = new OrdemServicoRepository();
    private final VeiculoRepository veiculoRepository = new VeiculoRepository();

    public OrdemServico abrir(OrdemServico os) {
        veiculoRepository.buscarPorId(os.getIdVeiculo())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Não é possível abrir ordem de serviço: veículo de id " + os.getIdVeiculo() + " não está cadastrado."));
        if (os.getValor() == null || os.getValor().compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("O valor do serviço não pode ser negativo.");
        if (os.getDescricao() == null || os.getDescricao().isBlank())
            throw new IllegalArgumentException("A descrição do problema é obrigatória.");
        os.setStatus("ABERTA");
        return repository.salvar(os);
    }

    public void concluir(int id) {
        OrdemServico os = buscarPorId(id);
        os.setStatus("CONCLUIDA");
        repository.atualizar(os);
    }

    public OrdemServico buscarPorId(int id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Ordem de serviço não encontrada com id " + id));
    }

    public List<OrdemServico> listarTodos() {
        return repository.listarTodos();
    }

    public List<OrdemServico> listarPorVeiculo(int idVeiculo) {
        return repository.listarPorVeiculo(idVeiculo);
    }

    public void atualizar(OrdemServico os) {
        repository.atualizar(os);
    }

    public void deletar(int id) {
        repository.deletar(id);
    }
}
