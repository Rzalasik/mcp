package br.edu.faculdade.clinicaveterinaria.model;

public class Animal {
    private int id;
    private String nome;
    private String especie;
    private String raca;
    private Tutor tutor;

    public Animal() {}

    public Animal(String nome, String especie, String raca, Tutor tutor) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.tutor = tutor;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }

    public Tutor getTutor() { return tutor; }
    public void setTutor(Tutor tutor) { this.tutor = tutor; }

    @Override
    public String toString() {
        return "Animal{id=" + id + ", nome='" + nome + "', especie='" + especie
                + "', raca='" + raca + "', tutor=" + (tutor != null ? tutor.getNome() : "null") + "}";
    }
}
