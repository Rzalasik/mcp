package br.edu.faculdade.oficina.model;

import java.math.BigDecimal;

public class OrdemServico {
    private int id;
    private Veiculo veiculo;
    private String descricao;
    private BigDecimal valor;
    private String status;

    public OrdemServico() {}

    public OrdemServico(Veiculo veiculo, String descricao, BigDecimal valor) {
        this.veiculo = veiculo;
        this.descricao = descricao;
        this.valor = valor;
        this.status = "ABERTA";
    }

    public boolean isAberta() { return "ABERTA".equals(status); }
    public boolean isConcluida() { return "CONCLUIDA".equals(status); }
    public void concluir() { this.status = "CONCLUIDA"; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Veiculo getVeiculo() { return veiculo; }
    public void setVeiculo(Veiculo veiculo) { this.veiculo = veiculo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "OrdemServico{id=" + id
                + ", veiculo=" + (veiculo != null ? veiculo.getPlaca() : "null")
                + ", descricao='" + descricao + "', valor=" + valor + ", status='" + status + "'}";
    }
}
