package com.trabajo.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.trabajo.Curso;
import com.trabajo.Estudiante;
import com.trabajo.Inscripcion;
import com.trabajo.Conexion.ConexionDB;
import com.trabajo.DTO.InscripcionDTO;

public class CursosInscritosDAO extends ObservableDAO{
    private final ConexionDB conexionDB;
    private CursoDAO cursoDAO;
    private EstudianteDAO estudianteDAO;
    private static CursosInscritosDAO instancia;

    private CursosInscritosDAO(ConexionDB conexionDB, CursoDAO cursoDAO, EstudianteDAO estudianteDAO) {
        this.conexionDB = conexionDB;
        this.cursoDAO = cursoDAO;
        this.estudianteDAO = estudianteDAO;
    }

    public static CursosInscritosDAO getInstancia(ConexionDB conexionDB, CursoDAO cursoDAO, EstudianteDAO estudianteDAO){
        if(instancia == null){
            synchronized(CursosInscritosDAO.class){
                if(instancia == null){
                    instancia = new CursosInscritosDAO(conexionDB, cursoDAO, estudianteDAO);
                }
            }
        }
        return instancia;
    }

    public void guardar(Inscripcion inscripcion) {
        try (PreparedStatement pstmt = conexionDB.getConexion().getConexion()
                .prepareStatement("insert INTO inscripcion (id, curso_id, ano, semestre, estudiante_id) VALUES (?, ?, ?, ?, ?)")) {
            pstmt.setDouble(1, inscripcion.getId());
            pstmt.setDouble(2, inscripcion.getCurso().getId());
            pstmt.setInt(3, inscripcion.getAno());
            pstmt.setInt(4, inscripcion.getSemestre());
            pstmt.setDouble(5, inscripcion.getEstudiante().getId());
            pstmt.executeUpdate();
            super.notificarCambio("Creacion", "CursosInscritos", "id: " + inscripcion.getId() + ", nombre curso: " + inscripcion.getCurso().getNombre() + ", nombre estudiante: " + inscripcion.getEstudiante().getNombre() + " " + inscripcion.getEstudiante().getApellidos());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(Double id) {
        try (PreparedStatement pstmt = conexionDB.getConexion().getConexion()
                .prepareStatement("DELETE FROM inscripcion WHERE id = ?")) {
            pstmt.setDouble(1, id);
            pstmt.executeUpdate();
            super.notificarCambio("Eliminacion", "Cursos Inscritos", "id: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizar(Inscripcion inscripcion) {
        try (PreparedStatement pstmt = conexionDB.getConexion().getConexion()
                .prepareStatement("UPDATE inscripcion SET ano = ?, semestre = ? WHERE id = ?")) {
            pstmt.setInt(1, inscripcion.getAno());
            pstmt.setInt(2, inscripcion.getSemestre());
            pstmt.setDouble(3, inscripcion.getId());
            pstmt.executeUpdate();
            super.notificarCambio("Actualizacion", "Cursos Inscritos", "id: " + inscripcion.getId() + ", nombre curso: " + inscripcion.getCurso().getNombre() + ", nombre estudiante:" + inscripcion.getEstudiante().getApellidos() + " " + inscripcion.getEstudiante().getApellidos());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Double obtenerSiguienteId() {
        Double maxId = 0.0;
        try (Statement stmt = conexionDB.getConexion().getConexion().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS max_id FROM inscripcion")) {
            if (rs.next()) {
                maxId = rs.getDouble("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxId + 1.0;
    }

    public List<InscripcionDTO> cargarTodos() {
        List<InscripcionDTO> lista = new ArrayList<>();
        try (Statement stmt = conexionDB.getConexion().getConexion().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM inscripcion")) {
            while (rs.next()) {
                Double id = rs.getDouble("id");
                Double curso_id = rs.getDouble("curso_id");
                int ano = rs.getInt("ano");
                int semestre = rs.getInt("semestre");
                Double estudiante_id = rs.getDouble("estudiante_id");

                Curso curso = cursoDAO.buscarPorId(curso_id);
                Estudiante estudiante = estudianteDAO.buscarPorId(estudiante_id);

                if(curso != null && estudiante != null){
                    lista.add(new InscripcionDTO(id, curso.getId(), curso.getNombre(), estudiante.getId(), estudiante.getNombre(), estudiante.getApellidos(), ano, semestre));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}