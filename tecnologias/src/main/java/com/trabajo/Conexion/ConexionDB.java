package com.trabajo.Conexion;

import java.sql.SQLException;

import com.trabajo.Factories.ConexionFactory;

public class ConexionDB {
    private Conexion conexion;

    public ConexionDB(){
        this.conexion = ConexionFactory.crearConexion();
    }

    public Conexion getConexion(){
        return conexion;
    }

    public void cerrarConexion() {
        try {
            if (conexion.getConexion() != null && !conexion.getConexion().isClosed()) {
                conexion.getConexion().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
