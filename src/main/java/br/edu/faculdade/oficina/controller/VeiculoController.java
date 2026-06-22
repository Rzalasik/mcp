package br.edu.faculdade.oficina.controller;

import br.edu.faculdade.oficina.model.Veiculo;
import br.edu.faculdade.oficina.service.VeiculoService;

import java.util.List;

public class VeiculoController {
    private final VeiculoService service = new VeiculoService();

    public Veiculo cadastrar(String placa, String modelo, int ano, int idCliente) {
        Veiculo salvo = service.cadastrar(placa, modelo, ano, idCliente);
        System.out.println("[Veículo cadastrado] " + salvo);
        return salvo;
    }

    public Veiculo buscar(int id) {
        Veiculo veiculo = service.buscarPorId(id);
        System.out.println("[Veículo encontrado] " + veiculo);
        return veiculo;
    }

    public List<Veiculo> listarTodos() {
        List<Veiculo> lista = service.listarTodos();
        System.out.println("[Lista de veículos]");
        lista.forEach(System.out::println);
        return lista;
    }

    public List<Veiculo> listarPorCliente(int idCliente) {
        List<Veiculo> lista = service.listarPorCliente(idCliente);
        System.out.println("[Veículos do cliente id=" + idCliente + "]");
        lista.forEach(System.out::println);
        return lista;
    }

    public void atualizar(Veiculo veiculo) {
        service.atualizar(veiculo);
        System.out.println("[Veículo atualizado] " + veiculo);
    }

    public void deletar(int id) {
        service.deletar(id);
        System.out.println("[Veículo removido] id=" + id);
    }
}
