package br.edu.faculdade.oficina;

import br.edu.faculdade.oficina.controller.ClienteController;
import br.edu.faculdade.oficina.controller.OrdemServicoController;
import br.edu.faculdade.oficina.controller.VeiculoController;
import br.edu.faculdade.oficina.model.Cliente;
import br.edu.faculdade.oficina.model.Veiculo;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        ClienteController clienteController = new ClienteController();
        VeiculoController veiculoController = new VeiculoController();
        OrdemServicoController ordemController = new OrdemServicoController();

        System.out.println("=== SISTEMA DE OFICINA MECÂNICA ===\n");

        // 1. Cadastro do cliente
        System.out.println("--- Cadastrando cliente ---");
        Cliente cliente = clienteController.cadastrar("João Mecânico", "11 98765-4321");

        // 2. Cadastro de veículos do cliente
        System.out.println("\n--- Cadastrando veículos ---");
        Veiculo gol = veiculoController.cadastrar("ABC-1234", "Volkswagen Gol", 2018, cliente.getId());
        Veiculo uno = veiculoController.cadastrar("DEF-5678", "Fiat Uno", 2015, cliente.getId());

        // 3. Abrir ordens de serviço (movimento)
        System.out.println("\n--- Abrindo ordens de serviço ---");
        var os1 = ordemController.abrir(gol.getId(), "Troca de óleo e filtro", new BigDecimal("250.00"));
        ordemController.abrir(gol.getId(), "Alinhamento e balanceamento", new BigDecimal("180.00"));
        ordemController.abrir(uno.getId(), "Revisão completa", new BigDecimal("500.00"));

        // 4. Concluir uma ordem
        System.out.println("\n--- Concluindo ordem de serviço ---");
        ordemController.concluir(os1.getId());

        // 5. Histórico de manutenções por veículo
        System.out.println("\n--- Histórico do Gol ---");
        ordemController.listarPorVeiculo(gol.getId());

        // 6. Veículos do cliente
        System.out.println("\n--- Veículos de " + cliente.getNome() + " ---");
        veiculoController.listarPorCliente(cliente.getId());

        // 7. Teste de regra: valor negativo
        System.out.println("\n--- Testando regra: valor negativo ---");
        try {
            ordemController.abrir(gol.getId(), "Teste inválido", new BigDecimal("-100.00"));
        } catch (IllegalArgumentException e) {
            System.out.println("[ERRO esperado] " + e.getMessage());
        }

        // 8. Teste de regra: veículo inexistente
        System.out.println("\n--- Testando regra: veículo inexistente ---");
        try {
            ordemController.abrir(9999, "Teste inválido", new BigDecimal("100.00"));
        } catch (IllegalArgumentException e) {
            System.out.println("[ERRO esperado] " + e.getMessage());
        }
    }
}
