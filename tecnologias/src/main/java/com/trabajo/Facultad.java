package com.trabajo;

public class Facultad {
    private Double id;
    private String nombre;
    private Persona decano;

    public Facultad(Double id, String nombre, Persona decano) {
        this.id = id;
        this.nombre = nombre;
        this.decano = decano;
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

    public Persona getDecano() {
        return this.decano;
    }

    public void setDecano(Persona decano) {
        this.decano = decano;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", decano='" + getDecano().getNombre() + " " + getDecano().getApellidos() + "'" +
            "}";
    }
}