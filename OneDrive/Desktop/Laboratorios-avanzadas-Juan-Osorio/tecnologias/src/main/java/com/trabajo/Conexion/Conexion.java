package com.trabajo.Conexion;

import java.sql.Connection;

public interface Conexion {
    Connection getConexion();

    String getCurrentDateTimeQuery();
}
