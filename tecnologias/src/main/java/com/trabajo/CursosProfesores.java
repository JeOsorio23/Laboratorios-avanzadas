package com.trabajo;

import java.util.ArrayList;
import java.util.List;

public class CursosProfesores implements Servicios {
    private List<CursoProfesor> cursoProfesores;

    public CursosProfesores(){
        cursoProfesores = new ArrayList<>();
    }

    public List<CursoProfesor> getCursoProfesores() {
        return this.cursoProfesores;
    }

    public void inscribirCurso(CursoProfesor cursoProfesor) {
        cursoProfesores.add(cursoProfesor);
    }

    public void eliminar(CursoProfesor cursoProfesor) { 
        cursoProfesores.remove(cursoProfesor);
    }

    public void actualizar(CursoProfesor cursoProfesor) {
        for (CursoProfesor i : cursoProfesores) {
            if (i.getId() == cursoProfesor.getId()) {
                i.setAno(cursoProfesor.getAno());
                i.setSemestre(cursoProfesor.getSemestre());
                i.setCurso(cursoProfesor.getCurso());
                break;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CursosProfesores:\n");
        for (CursoProfesor i : cursoProfesores) {
            sb.append("ID: ").append(i.getId()).append(", Profesor: ").append(i.getProfesor().getNombre())
              .append(", Año: ").append(i.getAno())
              .append(", Semestre: ").append(i.getSemestre())
              .append(", Curso: ").append(i.getCurso().getNombre()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String imprimirPosicion(int posicion) {
        return cursoProfesores.get(posicion).toString();
    }

    @Override
    public int cantidadActual() {
        return cursoProfesores.size();
    }

    @Override
    public void imprimirListado() {
        for(CursoProfesor i: cursoProfesores){
            i.toString();
        }
    }
}