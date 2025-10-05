package com.trabajo;

public class CursoProfesor {
    private Double id;
    private Profesor profesor;
    private int ano;
    private int semestre;
    private Curso curso;

    public CursoProfesor(Double id, Profesor profesor, int ano, int semestre, Curso curso) {
        this.id = id;
        this.profesor = profesor;
        this.ano = ano;
        this.semestre = semestre;
        this.curso = curso;
    }

    public Double getId() {
        return this.id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public Profesor getProfesor() {
        return this.profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public int getAno() {
        return this.ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getSemestre() {
        return this.semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public Curso getCurso() {
        return this.curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "{" +
            " profesor='" + getProfesor().getNombre() + " " + getProfesor().getApellidos() + "'" +
            ", ano='" + getAno() + "'" +
            ", semestre='" + getSemestre() + "'" +
            ", curso='" + getCurso().getNombre() + "'" +
            "}";
    }
}