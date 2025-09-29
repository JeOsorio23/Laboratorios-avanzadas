package com.trabajo;

import java.util.ArrayList;
import java.util.List;

public class InscripcionesPersonas {
    private List<Persona> personas;

    public InscripcionesPersonas(List<Persona> personas){
        personas = new ArrayList<>();
    }

    public void inscribir(Persona persona){
        personas.add(persona);
    }

    public void eliminar(Persona persona){
        personas.remove(persona);
    }

    public void actualizar(Persona persona){
        for(Persona p : personas){
            if(p.getId() == persona.getId()){
                p.setNombre(persona.getNombre());
                p.setApellidos(persona.getApellidos());
                p.setEmail(persona.getEmail());
                break;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("InscripcionesPersonas:\n");
        for (Persona pe : personas) {
            sb.append("ID: ").append(pe.getId()).append(", Nombre: ").append(pe.getNombre()).append(", Apellidos: ")
                .append(pe.getApellidos()).append(", email: ").append(pe.getEmail()).append("\n");
        }
        return sb.toString();
    }
}