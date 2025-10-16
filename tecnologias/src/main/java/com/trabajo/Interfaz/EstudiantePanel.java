package com.trabajo.Interfaz;

import com.trabajo.DAO.EstudianteDAO;
import com.trabajo.Estudiante;
import com.trabajo.Modelo.EstudianteTableModel;
import javax.swing.*;
import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.util.List;

public class EstudiantePanel extends JPanel {
    private JTextField nombreField, idField, apellidosField, emailField, codigoField, promedioField;
    private JCheckBox activoCheckBox;
    private JButton guardarButton, leerButton, actualizarButton, eliminarButton;
    private JTable estudianteTable;
    private EstudianteTableModel tableModel;

    private final EstudianteDAO estudianteDAO;

    public EstudiantePanel(EstudianteDAO estudianteDAO) {
        this.estudianteDAO = estudianteDAO;
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();

        // Inicialización del modelo de tabla y la tabla
        this.tableModel = new EstudianteTableModel();
        estudianteTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(estudianteTable);

        // Agregando componentes al panel principal
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Añadir eventos a los botones
        addEventosBotones();

        // Cargar datos iniciales al abrir el panel
        cargarEstudiantes();
    }

    // --- Métodos para la creación de componentes ---
    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        inputPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        inputPanel.add(nombreField);
        inputPanel.add(new JLabel("Apellidos:"));
        apellidosField = new JTextField();
        inputPanel.add(apellidosField);
        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("Código:"));
        codigoField = new JTextField();
        inputPanel.add(codigoField);
        inputPanel.add(new JLabel("Promedio:"));
        promedioField = new JTextField();
        inputPanel.add(promedioField);
        inputPanel.add(new JLabel("Activo:"));
        activoCheckBox = new JCheckBox();
        inputPanel.add(activoCheckBox);
        return inputPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        guardarButton = new JButton("Guardar");
        leerButton = new JButton("Leer");
        actualizarButton = new JButton("Actualizar");
        eliminarButton = new JButton("Eliminar");
        buttonPanel.add(guardarButton);
        buttonPanel.add(leerButton);
        buttonPanel.add(actualizarButton);
        buttonPanel.add(eliminarButton);
        return buttonPanel;
    }

    // --- Lógica de eventos y CRUD ---

    private void addEventosBotones() {
        guardarButton.addActionListener(e -> guardarEstudiante());
        leerButton.addActionListener(e -> cargarEstudiantes());
        actualizarButton.addActionListener(e -> actualizarEstudiante());
        eliminarButton.addActionListener(e -> eliminarEstudiante());

        // Evento para cargar datos en los campos al seleccionar una fila
        estudianteTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = estudianteTable.getSelectedRow();
                if (selectedRow != -1) {
                    Estudiante estudiante = tableModel.getEstudianteAt(selectedRow);
                    idField.setText(String.valueOf(estudiante.getId()));
                    nombreField.setText(estudiante.getNombre());
                    apellidosField.setText(estudiante.getApellidos());
                    emailField.setText(estudiante.getEmail());
                    codigoField.setText(String.valueOf(estudiante.getCodigo()));
                    promedioField.setText(String.valueOf(estudiante.getPromedio()));
                    activoCheckBox.setSelected(estudiante.getActivo());
                }
            }
        });
    }

    private void guardarEstudiante() {
        try {
            double id = Double.parseDouble(idField.getText());
            String nombre = nombreField.getText();
            String apellidos = apellidosField.getText();
            String email = emailField.getText();
            double codigo = Double.parseDouble(codigoField.getText());
            double promedio = Double.parseDouble(promedioField.getText());
            boolean activo = activoCheckBox.isSelected();

            Estudiante nuevoEstudiante = new Estudiante(id, nombre, apellidos, email, codigo, null, activo, promedio);
            estudianteDAO.insertar(nuevoEstudiante);

            cargarEstudiantes();
            JOptionPane.showMessageDialog(this, "Estudiante guardado con éxito.");
            limpiarCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID, código y promedio válidos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarEstudiantes() {
        List<Estudiante> estudiantes = estudianteDAO.obtenerTodos();
        tableModel.setEstudiantes(estudiantes);
    }

    private void actualizarEstudiante() {
        try {
            double id = Double.parseDouble(idField.getText());
            Estudiante estudianteParaActualizar = estudianteDAO.buscarPorId(id);
            if (estudianteParaActualizar != null) {
                estudianteParaActualizar.setNombre(nombreField.getText());
                estudianteParaActualizar.setApellidos(apellidosField.getText());
                estudianteParaActualizar.setEmail(emailField.getText());
                estudianteParaActualizar.setCodigo(Double.parseDouble(codigoField.getText()));
                estudianteParaActualizar.setPromedio(Double.parseDouble(promedioField.getText()));
                estudianteParaActualizar.setActivo(activoCheckBox.isSelected());

                estudianteDAO.actualizar(estudianteParaActualizar);
                JOptionPane.showMessageDialog(this, "Estudiante actualizado con éxito.");
                cargarEstudiantes();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Estudiante no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID válido para actualizar.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarEstudiante() {
        try {
            double id = Double.parseDouble(idField.getText());
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea eliminar al estudiante con ID: " + id + "?", "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                estudianteDAO.eliminar(id);
                JOptionPane.showMessageDialog(this, "Estudiante eliminado.");
                cargarEstudiantes();
                limpiarCampos();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID válido para eliminar.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        idField.setText("");
        nombreField.setText("");
        apellidosField.setText("");
        emailField.setText("");
        codigoField.setText("");
        promedioField.setText("");
        activoCheckBox.setSelected(false);
    }
}