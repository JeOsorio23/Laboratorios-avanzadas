package com.trabajo;

public class Estudiante extends Persona {
    private Double codigo;
    private Programa programa;
    private boolean activo;
    private Double promedio;

    public Estudiante(Double id, String nombre, String apellidos, String email, Double codigo, Programa programa, boolean activo, Double promedio) {
        super(id, nombre, apellidos, email);
        this.codigo = codigo;
        this.programa = programa;
        this.activo = activo;
        this.promedio = promedio;
    }

    public Double getCodigo() {
        return this.codigo;
    }

    public void setCodigo(Double codigo) {
        this.codigo = codigo;
    }

    public Programa getPrograma() {
        return this.programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public boolean isActivo() {
        return this.activo;
    }

    public boolean getActivo() {
        return this.activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Double getPromedio() {
        return this.promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", email='" + getEmail() + "'" +
            " codigo='" + getCodigo() + "'" +
            ", programa='" + getPrograma() + "'" +
            ", activo='" + isActivo() + "'" +
            ", promedio='" + getPromedio() + "'" +
            "}";
    }
}