package com.trabajo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursosInscritos implements Servicios {
    private List<Inscripcion> inscripciones;
    private Connection conn;

    public CursosInscritos(){
        inscripciones = new ArrayList<>();
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:~/cursos_inscritos;AUTO_SERVER=TRUE;DB_CLOSE_ON_EXIT=TRUE", "sa", "");
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS INSCRIPCION (id DOUBLE PRIMARY KEY, ano INT, semestre INT)");
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inscribirCurso(Inscripcion inscripcion) {
        inscripcion.setId(obtenerSiguienteId());
        
        inscripciones.add(inscripcion);
        guardarInformacion(inscripcion);
    }

    public void eliminar(Inscripcion inscripcion) {
        inscripciones.remove(inscripcion);
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM INSCRIPCION WHERE id = ?");
            pstmt.setDouble(1, inscripcion.getId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizar(Inscripcion inscripcion) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("UPDATE INSCRIPCION SET ano = ? WHERE id = ?");
            pstmt.setDouble(1, inscripcion.getAno());
            pstmt.setDouble(2, inscripcion.getId());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Inscripcion i : inscripciones) {
            if (i.getId() == inscripcion.getId()) {
                i.setAno(inscripcion.getAno());
                break;
            }
        }
    }

    public Double obtenerSiguienteId() {
        Double maxId = 0.0;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS max_id FROM INSCRIPCION");
            if (rs.next()) {
                maxId = rs.getDouble("max_id");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxId + 1.0;
    }

    public void guardarInformacion(Inscripcion inscripcion) {
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                "MERGE INTO INSCRIPCION (id, ano, semestre) VALUES (?, ?, ?)"
            );
            pstmt.setDouble(1, inscripcion.getId());
            pstmt.setInt(2, inscripcion.getAno());
            pstmt.setInt(3, inscripcion.getSemestre());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cargarDatos() {
        inscripciones.clear();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM INSCRIPCION");
            while (rs.next()) {
                Double id = rs.getDouble("id");
                int ano = rs.getInt("ano");
                int semestre = rs.getInt("semestre");

                Inscripcion inscripcion = new Inscripcion(id, null, ano, semestre, null);
                inscripciones.add(inscripcion);
            }
            rs.close();
            stmt.close();
            System.out.println("Los datos se han cargado correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Inscripcion> getLista() {
        return inscripciones;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CursosInscritos:\n");
        for (Inscripcion i : inscripciones) {
            sb.append("ID: ").append(i.getId())
              .append(", Ano: ").append(i.getAno())
              .append(", Semestre: ").append(i.getSemestre())
            .append("\n");
        }
        return sb.toString();
    }

    @Override
    public String imprimirPosicion(int posicion) {
        return inscripciones.get(posicion).toString();
    }

    @Override
    public int cantidadActual() {
       return inscripciones.size();
    }

    @Override
    public void imprimirListado() {
        for(Inscripcion i: inscripciones){
            i.toString();
        }
    }
}