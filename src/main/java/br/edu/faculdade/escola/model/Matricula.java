package br.edu.faculdade.escola.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Matricula {
    private int id;
    private int idAluno;
    private int idCurso;
    private LocalDate dataMatricula;
    private BigDecimal valor;

    public Matricula() {}

    public Matricula(int idAluno, int idCurso, LocalDate dataMatricula, BigDecimal valor) {
        this.idAluno = idAluno;
        this.idCurso = idCurso;
        this.dataMatricula = dataMatricula;
        this.valor = valor;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdAluno() { return idAluno; }
    public void setIdAluno(int idAluno) { this.idAluno = idAluno; }

    public int getIdCurso() { return idCurso; }
    public void setIdCurso(int idCurso) { this.idCurso = idCurso; }

    public LocalDate getDataMatricula() { return dataMatricula; }
    public void setDataMatricula(LocalDate dataMatricula) { this.dataMatricula = dataMatricula; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    @Override
    public String toString() {
        return "Matricula{id=" + id + ", idAluno=" + idAluno + ", idCurso=" + idCurso + ", data=" + dataMatricula + ", valor=" + valor + "}";
    }
}
