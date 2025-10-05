package com.trabajo;

import java.time.LocalDateTime;

public class Programa {
    private Double id;
    private String nombre;
    private Double duracion;
    private LocalDateTime registro;
    private Facultad facultad;

    public Programa(Double id, String nombre, Double duracion, LocalDateTime registro,Facultad facultad) {
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
        this.registro = registro;
        this.facultad = facultad;
    }

    public Double getId() {
        return this.id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getDuracion() {
        return this.duracion;
    }

    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }

    public LocalDateTime getRegistro() {
        return this.registro;
    }

    public void setRegistro(LocalDateTime registro) {
        this.registro = registro;
    }

    public Facultad getFacultad() {
        return this.facultad;
    }

    public void setFacultad(Facultad facultad) {
        this.facultad = facultad;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", duracion='" + getDuracion() + "'" +
            ", registro='" + getRegistro() + "'" +
            ", facultad='" + (facultad != null ? facultad.getNombre() : "Sin programa") + "'" +
            "}";
    }
}