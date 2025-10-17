package com.trabajo.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.trabajo.Curso;
import com.trabajo.Conexion.ConexionDB;

public class CursoDAO extends ObservableDAO{
    private final ConexionDB conexionDB;
    private static CursoDAO instancia;

    private CursoDAO(ConexionDB conexionDB) {
        this.conexionDB = conexionDB;
    }

    public static CursoDAO getInstancia(ConexionDB conexionDB){
        if(instancia == null){
            synchronized(CursoDAO.class){
                if(instancia == null){
                    instancia = new CursoDAO(conexionDB);
                }
            }
        }
        return instancia;
    }

    public List<Curso> obtenerTodos() {
        List<Curso> lista = new ArrayList<>();
        try (Statement stmt = conexionDB.getConexion().getConexion().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM curso")) {
            while (rs.next()) {
                Double id = rs.getDouble("id");
                String nombre = rs.getString("nombre");
                boolean activo = rs.getBoolean("activo");

                lista.add(new Curso(id, nombre, null, activo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Curso buscarPorId(Double id) {
    Curso curso = null;
    try (PreparedStatement pstmt = conexionDB.getConexion().getConexion()
            .prepareStatement("SELECT * FROM curso WHERE id = ?")) {
        pstmt.setDouble(1, id);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                Double cursoId = rs.getDouble("id");
                String nombre = rs.getString("nombre");
                //Double programaId = rs.getDouble("programa_id"); 
                boolean activo = rs.getBoolean("activo");

                curso = new Curso(cursoId, nombre, null, activo);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return curso;
}
}
