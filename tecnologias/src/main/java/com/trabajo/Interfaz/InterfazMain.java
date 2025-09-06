package com.trabajo.Interfaz;

import com.trabajo.Curso;
import com.trabajo.CursosInscritos;
import com.trabajo.Estudiante;
import com.trabajo.Facultad;
import com.trabajo.Inscripcion;
import com.trabajo.Persona;
import com.trabajo.Programa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InterfazMain extends JFrame {
    
    private JComboBox<Curso> comboCursos;
    private JComboBox<Estudiante> comboEstudiantes;
    private JTextField txtAno;
    private JTextField txtSemestre;
    private JButton btnInscribir;

    private TablaInscripciones tablaPanel;

    private CursosInscritos gestorCursos;

    private List<Curso> listaCursos;
    private List<Estudiante> listaEstudiantes;

    public InterfazMain() {
        super("Gestión de Inscripciones y Cursos");
        this.gestorCursos = new CursosInscritos();
        
        setLayout(new BorderLayout(10, 10));

        inicializarDatosDummy();
        
        configurarFormulario();
        
        tablaPanel = new TablaInscripciones(gestorCursos);
        this.add(tablaPanel, BorderLayout.CENTER);
        
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void inicializarDatosDummy() {
        listaCursos = new ArrayList<>();
        Programa progDummy = new Programa(1.0, "Ingenieria de Sistemas", 10.0, LocalDateTime.now(), new Facultad(1.0, "Ingeniería", new Persona(1.0, "Facultad", "Dummy", "fac.dummy@gmail.com")));
        listaCursos.add(new Curso(1.0, "Matematicas I", progDummy, true));
        listaCursos.add(new Curso(2.0, "Fisica General", progDummy, true));
        listaCursos.add(new Curso(3.0, "Programacion Orientada a Objetos", progDummy, true));
        
        listaEstudiantes = new ArrayList<>();
        listaEstudiantes.add(new Estudiante(1.0, "Jose", "Alvarez", "joseal@gmail.com", 1234.0, progDummy, true, 4.0));
        listaEstudiantes.add(new Estudiante(2.0, "Santiago", "Perez", "santip@gmail.com", 5678.0, progDummy, true, 3.5));
        listaEstudiantes.add(new Estudiante(3.0, "Maria", "Lopez", "maril@gmail.com", 9101.0, progDummy, true, 4.2));
    }

    private void configurarFormulario() {
        JPanel formularioPanel = new JPanel(new GridLayout(5, 1, 10, 5));
        
        comboCursos = new JComboBox<>(listaCursos.toArray(new Curso[0]));
        comboEstudiantes = new JComboBox<>(listaEstudiantes.toArray(new Estudiante[0]));
        txtAno = new JTextField(10);
        txtSemestre = new JTextField(10);
        btnInscribir = new JButton("Inscribir Curso");
        
        comboCursos.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Curso) {
                    setText(((Curso) value).getNombre());
                }
                return this;
            }
        });
        
        comboEstudiantes.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Estudiante) {
                    setText(((Estudiante) value).getNombre() + " " + ((Estudiante) value).getApellidos());
                }
                return this;
            }
        });

        formularioPanel.add(new JLabel("Curso:"));
        formularioPanel.add(comboCursos);
        formularioPanel.add(new JLabel("Estudiante:"));
        formularioPanel.add(comboEstudiantes);
        formularioPanel.add(new JLabel("Año:"));
        formularioPanel.add(txtAno);
        formularioPanel.add(new JLabel("Semestre:"));
        formularioPanel.add(txtSemestre);
        formularioPanel.add(new JLabel(""));
        formularioPanel.add(btnInscribir);
        
        this.add(formularioPanel, BorderLayout.NORTH);
        
        btnInscribir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inscribirCurso();
            }
        });
    }

    private void inscribirCurso() {
        try {
            Curso cursoSeleccionado = (Curso) comboCursos.getSelectedItem();
            Estudiante estudianteSeleccionado = (Estudiante) comboEstudiantes.getSelectedItem();
            int ano = Integer.parseInt(txtAno.getText());
            int semestre = Integer.parseInt(txtSemestre.getText());
            
            if (cursoSeleccionado == null || estudianteSeleccionado == null) {
                 JOptionPane.showMessageDialog(this, "Por favor, seleccione un curso y un estudiante.", "Error de Selección", JOptionPane.ERROR_MESSAGE);
                 return;
            }

            Double idInscripcion = gestorCursos.cantidadActual() + 1.0;
            Inscripcion nuevaInscripcion = new Inscripcion(idInscripcion, cursoSeleccionado, ano, semestre, estudianteSeleccionado);
            
            gestorCursos.inscribirCurso(nuevaInscripcion);
            
            tablaPanel.agregarFila(nuevaInscripcion);
            
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Inscripción creada correctamente.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores válidos para el año y semestre.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al inscribir el curso: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        txtAno.setText("");
        txtSemestre.setText("");
    }
}