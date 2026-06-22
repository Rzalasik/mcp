package br.edu.faculdade.oficina.model;

public class Veiculo {
    private int id;
    private String placa;
    private String modelo;
    private int ano;
    private Cliente cliente;

    public Veiculo() {}

    public Veiculo(String placa, String modelo, int ano, Cliente cliente) {
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
        this.cliente = cliente;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    @Override
    public String toString() {
        return "Veiculo{id=" + id + ", placa='" + placa + "', modelo='" + modelo
                + "', ano=" + ano + ", cliente=" + (cliente != null ? cliente.getNome() : "null") + "}";
    }
}
