package com.trabajo.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class H2Conexion implements Conexion{
    private Connection connection;

    public H2Conexion(){
        try{
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/cursos_inscritos;AUTO_SERVER=TRUE;DB_CLOSE_ON_EXIT=TRUE", "sa", "");
            inicializarTablas();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void inicializarTablas(){
        try(Statement statement = connection.createStatement()){
            statement.execute("CREATE TABLE IF NOT EXISTS profesor (\r\n" +
                                "    id DOUBLE PRIMARY KEY,\r\n" +
                                "    nombre varchar(100) NOT NULL,\r\n" +
                                "    apellidos varchar(100) NOT NULL,\r\n" +
                                "    email varchar(200) NOT NULL,\r\n" +
                                "    tipo_contrato VARCHAR(50) NOT NULL\r\n" +
                                ")");
            statement.execute("CREATE TABLE IF NOT EXISTS curso (\r\n" +
                                "    id INT PRIMARY KEY,\r\n" +
                                "    nombre VARCHAR(100) NOT NULL,\r\n" +
                                "    programa_id DOUBLE,\r\n" +
                                "    activo BOOLEAN NOT NULL\r\n" +
                                ")");
            statement.execute("CREATE TABLE IF NOT EXISTS estudiante (\r\n" +
                                "    id DOUBLE PRIMARY KEY,\r\n" +
                                "    nombre varchar(100) NOT NULL,\r\n" +
                                "    apellidos varchar(100) NOT NULL,\r\n" +
                                "    email varchar(200) NOT NULL,\r\n" +
                                "    codigo DOUBLE NOT NULL,\r\n" +
                                "    programa_id DOUBLE,\r\n" +
                                "    activo BOOLEAN NOT NULL,\r\n" +
                                "    promedio DOUBLE\r\n" +
                                ")");
            statement.execute("CREATE TABLE IF NOT EXISTS inscripcion(\r\n" +
                                "    id DOUBLE PRIMARY KEY,\r\n" +
                                "    curso_id INT NOT NULL,\r\n" +
                                "    ano INT NOT NULL,\r\n" +
                                "    semestre INT NOT NULL,\r\n" +
                                "    estudiante_id DOUBLE NOT NULL,\r\n" +
                                "    CONSTRAINT fk_inscripcion_curso FOREIGN KEY (curso_id) REFERENCES curso(id),\r\n" +
                                "    CONSTRAINT fk_inscripcion_estudiante FOREIGN KEY (estudiante_id) REFERENCES estudiante(id)\r\n" +
                                ")");
            //Apartir de aqui se debe quitar una vez funcione las inscripciones en la interfaz
            statement.execute("MERGE INTO curso (id, nombre, programa_id, activo) VALUES\r\n" +
                                "(101, 'Matemáticas I', 1, TRUE),\r\n" +
                                "(102, 'Física General', 1, TRUE),\r\n" +
                                "(103, 'Programación Orientada a Objetos', 1, TRUE),\r\n" +
                                "(104, 'Bases de Datos', 1, TRUE),\r\n" +
                                "(105, 'Historia de la Ingeniería', 2, FALSE)");
            statement.execute("MERGE INTO estudiante (id, nombre, apellidos, email, codigo, programa_id, activo, promedio) VALUES\r\n" +
                                "(1001, 'Jose', 'Alvarez', 'jose.alvarez@uni.edu', 2023001, 1, TRUE, 4.2),\r\n" +
                                "(1002, 'Santiago', 'Perez', 'santiago.perez@uni.edu', 2023002, 1, TRUE, 3.8),\r\n" +
                                "(1003, 'Maria', 'Lopez', 'maria.lopez@uni.edu', 2023003, 1, TRUE, 4.5),\r\n" +
                                "(1004, 'Camila', 'Rodriguez', 'camila.rodriguez@uni.edu', 2023004, 2, TRUE, 3.9),\r\n" + 
                                "(1005, 'Daniel', 'Gonzalez', 'daniel.gonzalez@uni.edu', 2023005, 2, FALSE, 2.7)");
            statement.execute("MERGE INTO inscripcion (id, curso_id, ano, semestre, estudiante_id) VALUES\r\n" +
                                "(5001, 101, 2024, 1, 1001),\r\n" +
                                "(5002, 102, 2024, 1, 1002),\r\n" +
                                "(5003, 103, 2024, 2, 1003),\r\n" +
                                "(5004, 104, 2025, 1, 1001),\r\n" +
                                "(5005, 105, 2023, 2, 1004),\r\n" +
                                "(5006, 101, 2025, 1, 1005);");
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public Connection getConexion(){
        return connection;
    }

    public void cerrarConexion(){
        try{
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
