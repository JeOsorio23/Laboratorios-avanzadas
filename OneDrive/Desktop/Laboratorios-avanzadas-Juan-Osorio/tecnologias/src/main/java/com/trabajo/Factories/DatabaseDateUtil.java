package com.trabajo.Factories;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Properties;

public class DatabaseDateUtil {
    public static String getCurrentDateTimeQuery() {
        Properties properties = new Properties();
        try (InputStream inputStream = ConexionFactory.class
                .getClassLoader()
                .getResourceAsStream(ConexionFactory.config)) {

            properties.load(inputStream);
            String database = properties.getProperty("database.type");

            if ("h2".equalsIgnoreCase(database)) {
                return "SELECT NOW()";
            } else if ("mysql".equalsIgnoreCase(database)) {
                return "SELECT NOW()";
            } else if ("oracle".equalsIgnoreCase(database)) {
                return "SELECT SYSDATE FROM DUAL";
            } else {
                throw new IllegalArgumentException("Tipo de base de datos no reconocido: " + database);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al leer configuración: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene la fecha/hora actual directamente de la base de datos
     */
    public static Timestamp getCurrentDateTime(Connection connection) {
        String query = getCurrentDateTimeQuery();

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getTimestamp(1);
            }
            throw new RuntimeException("No se pudo obtener la fecha de la base de datos");
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener fecha de BD: " + e.getMessage(), e);
        }
    }
}