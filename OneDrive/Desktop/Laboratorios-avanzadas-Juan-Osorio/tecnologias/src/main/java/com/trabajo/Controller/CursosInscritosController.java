package com.trabajo.Controller;

import javax.swing.JOptionPane;

import com.trabajo.Curso;
import com.trabajo.Estudiante;
import com.trabajo.Inscripcion;
import com.trabajo.DAO.CursosInscritosDAO;
import com.trabajo.Interfaz.InterfazMain;

public class CursosInscritosController {
    private InterfazMain interfazMain;
    private CursosInscritosDAO cursosInscritosDAO;

    public CursosInscritosController(InterfazMain interfazMain, CursosInscritosDAO cursosInscritosDAO){
        this.interfazMain = interfazMain;
        this.cursosInscritosDAO = cursosInscritosDAO;
        
        this.interfazMain.addInscribirListener(e -> inscribirCurso());
    }
    
    private void inscribirCurso() {
        try {
            Curso cursoSeleccionado = interfazMain.getCursoSeleccionado();
            Estudiante estudianteSeleccionado = interfazMain.getEstudianteSeleccionado();
            int ano = interfazMain.getAno();
            int semestre = interfazMain.getSemestre();

            if (cursoSeleccionado == null || estudianteSeleccionado == null) {
                JOptionPane.showMessageDialog(interfazMain, "Por favor, seleccione un curso y un estudiante.",
                        "Error de Selección", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Double idInscripcion = cursosInscritosDAO.obtenerSiguienteId();
            Inscripcion nuevaInscripcion = new Inscripcion(idInscripcion, cursoSeleccionado, ano, semestre,
                    estudianteSeleccionado);

            cursosInscritosDAO.guardar(nuevaInscripcion);
            
            interfazMain.refrescarTabla();
            interfazMain.limpiarCampos();

            JOptionPane.showMessageDialog(interfazMain, "Inscripción creada correctamente.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(interfazMain, "Por favor, ingrese valores válidos para el año y semestre.",
                    "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(interfazMain, "Ocurrió un error al inscribir el curso: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
