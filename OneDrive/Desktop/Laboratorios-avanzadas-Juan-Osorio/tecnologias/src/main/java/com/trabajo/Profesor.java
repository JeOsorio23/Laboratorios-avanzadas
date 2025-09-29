package com.trabajo;

public class Profesor extends Persona {
    private String tipoContrato;

    public Profesor(Double id, String nombre, String apellidos, String email, String tipoContrato) {
        super(id, nombre, apellidos, email);
        this.tipoContrato = tipoContrato;
    }

    public String getTipoContrato() {
        return this.tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", email='" + getEmail() + "'" +
            " tipoContrato='" + getTipoContrato() + "'" +
            "}";
    }
}