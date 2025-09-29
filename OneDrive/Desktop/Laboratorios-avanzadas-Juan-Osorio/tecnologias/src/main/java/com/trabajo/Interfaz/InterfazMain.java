package com.trabajo.Interfaz;

import com.trabajo.Curso;
import com.trabajo.Estudiante;
import com.trabajo.Conexion.ConexionDB;
import com.trabajo.DAO.CursoDAO;
import com.trabajo.DAO.CursosInscritosDAO;
import com.trabajo.DAO.EstudianteDAO;
import com.trabajo.Factories.DAOFactory;
import com.trabajo.Interfaz.EstudiantePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class InterfazMain extends JFrame {
    private CursoDAO cursoDAO;
    private EstudianteDAO estudianteDAO;
    private CursosInscritosDAO inscripcionDAO;

    private JComboBox<Curso> comboCursos;
    private JComboBox<Estudiante> comboEstudiantes;
    private JTextField txtAno;
    private JTextField txtSemestre;
    private JButton btnInscribir;

    private TablaInscripciones tablaPanel;

    private List<Curso> listaCursos;
    private List<Estudiante> listaEstudiantes;

    public InterfazMain(DAOFactory factory, ConexionDB conexion) {
        super("Gestión de Inscripciones y Cursos");
        
        this.cursoDAO = factory.createCursoDAO(conexion);
        this.estudianteDAO = factory.createEstudianteDAO(conexion);
        this.inscripcionDAO = factory.createInscripcionesDAO(conexion);

        setLayout(new BorderLayout(10, 10));

        JTabbedPane tabs = new JTabbedPane();
        // tabs.addTab("Estudiantes", new EstudianteTableModel());
        tabs.addTab("Estudiantes", new EstudiantePanel(estudianteDAO));

        JPanel inscripcionesPanel = new JPanel(new BorderLayout(10, 10));
        inscripcionesPanel.add(configurarFormulario(), BorderLayout.NORTH);
        inscripcionesPanel.add(new TablaInscripciones(inscripcionDAO), BorderLayout.CENTER);

        tabs.addTab("Inscripciones", inscripcionesPanel);
        tabs.addTab("Cursos", new JPanel());
        tabs.addTab("profesores", new JPanel());
        tabs.addTab("Reportes", new JPanel());

        add(tabs, BorderLayout.CENTER);
        /*
         * tablaPanel = new TablaInscripciones(inscripcionDAO);
         * this.add(tablaPanel, BorderLayout.CENTER);
         */

        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel configurarFormulario() {
        JPanel formularioPanel = new JPanel(new GridLayout(5, 1, 10, 5));

        listaCursos = cursoDAO.obtenerTodos();
        listaEstudiantes = estudianteDAO.obtenerTodos();

        comboCursos = new JComboBox<>(listaCursos.toArray(new Curso[0]));
        comboEstudiantes = new JComboBox<>(listaEstudiantes.toArray(new Estudiante[0]));
        txtAno = new JTextField(10);
        txtSemestre = new JTextField(10);
        btnInscribir = new JButton("Inscribir Curso");

        comboCursos.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Curso) {
                    setText(((Curso) value).getNombre());
                }
                return this;
            }
        });

        comboEstudiantes.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
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

        return formularioPanel;
    }

    public void addInscribirListener(ActionListener listener) {
        btnInscribir.addActionListener(listener);
    }

    public Curso getCursoSeleccionado() {
        return (Curso) comboCursos.getSelectedItem();
    }

    public Estudiante getEstudianteSeleccionado() {
        return (Estudiante) comboEstudiantes.getSelectedItem();
    }

    public int getAno() {
        return Integer.parseInt(txtAno.getText());
    }

    public int getSemestre() {
        return Integer.parseInt(txtSemestre.getText());
    }

    public void refrescarTabla() {
        tablaPanel.cargarDatosEnTabla();
    }

    public void limpiarCampos() {
        txtAno.setText("");
        txtSemestre.setText("");
    }
}