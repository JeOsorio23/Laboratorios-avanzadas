package com.trabajo.DTO;

public class InscripcionDTO {
    private Double idInscripcion;
    private Double idCurso;
    private String nombreCurso;
    private Double idEstudiante;
    private String nombreEstudiante;
    private String apellidoEstudiante;
    private int ano;
    private int semestre;

    public InscripcionDTO(Double idInscripcion, Double idCurso, String nombreCurso, Double idEstudiante, String nombreEstudiante, String apellidoEstudiante, int ano, int semestre) {
        this.idInscripcion = idInscripcion;
        this.idCurso = idCurso;
        this.nombreCurso = nombreCurso;
        this.idEstudiante = idEstudiante;
        this.nombreEstudiante = nombreEstudiante;
        this.apellidoEstudiante = apellidoEstudiante;
        this.ano = ano;
        this.semestre = semestre;
    }

    public Double getIdInscripcion() {
        return this.idInscripcion;
    }

    public void setIdInscripcion(Double idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public Double getIdCurso() {
        return this.idCurso;
    }

    public void setIdCurso(Double idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombreCurso() {
        return this.nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public Double getIdEstudiante() {
        return this.idEstudiante;
    }

    public void setIdEstudiante(Double idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getNombreEstudiante() {
        return this.nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getApellidoEstudiante() {
        return this.apellidoEstudiante;
    }

    public void setApellidoEstudiante(String apellidoEstudiante) {
        this.apellidoEstudiante = apellidoEstudiante;
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

}
