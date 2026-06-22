package br.edu.faculdade.clinicaveterinaria.controller;

import br.edu.faculdade.clinicaveterinaria.model.Consulta;
import br.edu.faculdade.clinicaveterinaria.service.ConsultaService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ConsultaController {
    private final ConsultaService service = new ConsultaService();

    public Consulta registrar(int idAnimal, LocalDate data, String motivo, BigDecimal valor) {
        Consulta salva = service.registrar(idAnimal, data, motivo, valor);
        System.out.println("[Consulta registrada] " + salva);
        return salva;
    }

    public Consulta buscar(int id) {
        Consulta consulta = service.buscarPorId(id);
        System.out.println("[Consulta encontrada] " + consulta);
        return consulta;
    }

    public List<Consulta> listarTodos() {
        List<Consulta> lista = service.listarTodos();
        System.out.println("[Lista de consultas]");
        lista.forEach(System.out::println);
        return lista;
    }

    public List<Consulta> listarPorAnimal(int idAnimal) {
        List<Consulta> lista = service.listarPorAnimal(idAnimal);
        System.out.println("[Histórico de consultas do animal id=" + idAnimal + "]");
        lista.forEach(System.out::println);
        return lista;
    }

    public void atualizar(Consulta consulta) {
        service.atualizar(consulta);
        System.out.println("[Consulta atualizada] " + consulta);
    }

    public void deletar(int id) {
        service.deletar(id);
        System.out.println("[Consulta removida] id=" + id);
    }
}
