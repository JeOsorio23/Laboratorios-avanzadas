package com.trabajo.Factories;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.trabajo.Conexion.ConexionDB;

public class MainDAOFactory {
    private static final String config = "config.properties";

    public static DAOFactory getFactory(ConexionDB conexionDB){
        Properties properties = new Properties();
        try(InputStream inputStream = MainDAOFactory.class
                .getClassLoader()
                .getResourceAsStream(config)){
            properties.load(inputStream);
            String database = properties.getProperty("database.type");

            if("h2".equalsIgnoreCase(database)){
                return H2DAOFactory.getInstancia(conexionDB);
            } else if ("oracle".equalsIgnoreCase(database)){
                return OracleDAOFactory.getInstancia(conexionDB);
            } else if ("mysql".equalsIgnoreCase(database)){
                return MySQLDAOFactory.getInstancia(conexionDB);
            } else {
                throw new IllegalArgumentException("Tipo de base de datos no encontrado o soportado" + database);
            }
        } catch(IOException e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo leer el archivo de configuracion");
        }
    }
}
