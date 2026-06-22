package br.edu.faculdade.escola.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Matricula {
    private int id;
    private Aluno aluno;
    private Curso curso;
    private LocalDate dataMatricula;
    private BigDecimal valor;

    public Matricula() {}

    public Matricula(Aluno aluno, Curso curso, LocalDate dataMatricula, BigDecimal valor) {
        this.aluno = aluno;
        this.curso = curso;
        this.dataMatricula = dataMatricula;
        this.valor = valor;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Aluno getAluno() { return aluno; }
    public void setAluno(Aluno aluno) { this.aluno = aluno; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }

    public LocalDate getDataMatricula() { return dataMatricula; }
    public void setDataMatricula(LocalDate dataMatricula) { this.dataMatricula = dataMatricula; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    @Override
    public String toString() {
        return "Matricula{id=" + id
                + ", aluno=" + (aluno != null ? aluno.getNome() : "null")
                + ", curso=" + (curso != null ? curso.getNome() : "null")
                + ", data=" + dataMatricula + ", valor=" + valor + "}";
    }
}
