package com.trabajo;

public class Inscripcion {
    private Double id;
    private Curso curso;
    private int ano;
    private int semestre;
    private Estudiante estudiante;

    public Inscripcion(Double id, Curso curso, int ano, int semestre, Estudiante estudiante) {
        this.id = id;
        this.curso = curso;
        this.ano = ano;
        this.semestre = semestre;
        this.estudiante = estudiante;
    }

    public Double getId() {
        return this.id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public Curso getCurso() {
        return this.curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
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

    public Estudiante getEstudiante() {
        return this.estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" + 
            " curso='" + getCurso().getNombre() + "'" +
            ", ano='" + getAno() + "'" +
            ", semestre='" + getSemestre() + "'" +
            ", estudiante='" + getEstudiante().getNombre() + " " + getEstudiante().getApellidos() + "'" +
            "}";
    }
}