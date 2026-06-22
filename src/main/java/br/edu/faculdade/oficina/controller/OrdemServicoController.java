package br.edu.faculdade.oficina.controller;

import br.edu.faculdade.oficina.model.OrdemServico;
import br.edu.faculdade.oficina.service.OrdemServicoService;

import java.math.BigDecimal;
import java.util.List;

public class OrdemServicoController {
    private final OrdemServicoService service = new OrdemServicoService();

    public OrdemServico abrir(int idVeiculo, String descricao, BigDecimal valor) {
        OrdemServico os = new OrdemServico(idVeiculo, descricao, valor, "ABERTA");
        OrdemServico salva = service.abrir(os);
        System.out.println("[Ordem de serviço aberta] " + salva);
        return salva;
    }

    public void concluir(int id) {
        service.concluir(id);
        System.out.println("[Ordem de serviço concluída] id=" + id);
    }

    public OrdemServico buscar(int id) {
        OrdemServico os = service.buscarPorId(id);
        System.out.println("[Ordem encontrada] " + os);
        return os;
    }

    public List<OrdemServico> listarTodos() {
        List<OrdemServico> lista = service.listarTodos();
        System.out.println("[Lista de ordens de serviço]");
        lista.forEach(System.out::println);
        return lista;
    }

    public List<OrdemServico> listarPorVeiculo(int idVeiculo) {
        List<OrdemServico> lista = service.listarPorVeiculo(idVeiculo);
        System.out.println("[Histórico de manutenções do veículo id=" + idVeiculo + "]");
        lista.forEach(System.out::println);
        return lista;
    }

    public void deletar(int id) {
        service.deletar(id);
        System.out.println("[Ordem removida] id=" + id);
    }
}
