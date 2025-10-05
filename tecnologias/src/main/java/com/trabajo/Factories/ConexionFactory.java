package com.trabajo.Factories;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.trabajo.Conexion.Conexion;
import com.trabajo.Conexion.H2Conexion;
import com.trabajo.Conexion.MySQLConexion;
import com.trabajo.Conexion.OracleConexion;

public class ConexionFactory{
    public static final String config = "config.properties";

    public static Conexion crearConexion(){
        Properties properties = new Properties();
        try(InputStream inputStream = ConexionFactory.class
                .getClassLoader()
                .getResourceAsStream(config)){
            properties.load(inputStream);
            String database = properties.getProperty("database.type");
            if("h2".equalsIgnoreCase(database)){
                return H2Conexion.getInstancia();
            } else if ("mysql".equalsIgnoreCase(database)){
                return MySQLConexion.getInstancia();
            } else if ("oracle".equalsIgnoreCase(database)){
                return OracleConexion.getInstancia();
            } else {
                throw new IllegalArgumentException("Tipo de base de datos no reconocida o soportada: " + database);
            }
        } catch(IOException e){
            e.printStackTrace();
            throw new RuntimeException("No se pudo leer el archivo de configuracion" + config);
        }
    }
}
