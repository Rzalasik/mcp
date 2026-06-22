package br.edu.faculdade.clinicaveterinaria.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Consulta {
    private int id;
    private Animal animal;
    private LocalDate data;
    private String motivo;
    private BigDecimal valor;

    public Consulta() {}

    public Consulta(Animal animal, LocalDate data, String motivo, BigDecimal valor) {
        this.animal = animal;
        this.data = data;
        this.motivo = motivo;
        this.valor = valor;
    }

    public boolean isValorValido() {
        return valor != null && valor.compareTo(BigDecimal.ZERO) >= 0;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Animal getAnimal() { return animal; }
    public void setAnimal(Animal animal) { this.animal = animal; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    @Override
    public String toString() {
        return "Consulta{id=" + id
                + ", animal=" + (animal != null ? animal.getNome() : "null")
                + ", data=" + data + ", motivo='" + motivo + "', valor=" + valor + "}";
    }
}
