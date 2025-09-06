package com.trabajo.Interfaz;

import com.trabajo.Inscripcion;
import com.trabajo.CursosInscritos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TablaInscripciones extends JPanel {

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private CursosInscritos gestorCursos;

    public TablaInscripciones(CursosInscritos gestorCursos) {
        this.gestorCursos = gestorCursos;
        this.setLayout(new BorderLayout());
        
        configurarTabla();
        cargarDatosEnTabla();
    }

    private void configurarTabla() {
        
        String[] columnas = {"ID Inscripción", "ID Curso", "Nombre Curso", "ID Estudiante", "Nombre Estudiante", "Año", "Semestre"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        this.add(scrollPane, BorderLayout.CENTER);
    }
    
    public void cargarDatosEnTabla() {
        modeloTabla.setRowCount(0);
        List<Inscripcion> lista = gestorCursos.getLista();
        for (Inscripcion inscripcion : lista) {
            Object[] fila = {
                inscripcion.getId(),
                inscripcion.getCurso() != null ? inscripcion.getCurso().getId() : "N/A",
                inscripcion.getCurso() != null ? inscripcion.getCurso().getNombre() : "N/A",
                inscripcion.getEstudiante() != null ? inscripcion.getEstudiante().getId() : "N/A",
                inscripcion.getEstudiante() != null ? inscripcion.getEstudiante().getNombre() + " " + inscripcion.getEstudiante().getApellidos() : "N/A",
                inscripcion.getAno(),
                inscripcion.getSemestre()
            };
            modeloTabla.addRow(fila);
        }
    }

    public void agregarFila(Inscripcion inscripcion) {
        Object[] nuevaFila = {
            inscripcion.getId(),
            inscripcion.getCurso().getId(),
            inscripcion.getCurso().getNombre(),
            inscripcion.getEstudiante().getId(),
            inscripcion.getEstudiante().getNombre() + " " + inscripcion.getEstudiante().getApellidos(),
            inscripcion.getAno(),
            inscripcion.getSemestre()
        };
        modeloTabla.addRow(nuevaFila);
    }
}