package com.trabajo.Modelo;

import com.trabajo.Estudiante;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class EstudianteTableModel extends AbstractTableModel {
    private List<Estudiante> estudiantes = new ArrayList<>();
    private final String[] columnas = { "ID", "Nombre", "Apellidos", "Código", "Promedio" };

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
        fireTableDataChanged(); // Notifica a la tabla que los datos han cambiado
    }

    /**
     * Obtiene un objeto Estudiante de la lista por su índice de fila.
     * 
     * @param rowIndex El índice de la fila en la tabla.
     * @return El objeto Estudiante en la fila especificada.
     */
    public Estudiante getEstudianteAt(int rowIndex) {
        return estudiantes.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return estudiantes.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Estudiante estudiante = estudiantes.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return estudiante.getId();
            case 1:
                return estudiante.getNombre();
            case 2:
                return estudiante.getApellidos();
            case 3:
                return estudiante.getCodigo();
            case 4:
                return estudiante.getPromedio();
            default:
                return null;
        }
    }
}