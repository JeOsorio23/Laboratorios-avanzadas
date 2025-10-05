package com.trabajo.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConexion implements Conexion {
    private Connection connection;
    private static MySQLConexion instancia;

    private MySQLConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cursos_inscritos", "root",
                    "1234");
            inicializarTablas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MySQLConexion getInstancia(){
        if(instancia == null){
            synchronized(MySQLConexion.class){
                if(instancia == null){
                    instancia = new MySQLConexion();
                }
            }
        }
        return instancia;
    }

    private void inicializarTablas() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS profesor (" +
                    "    id DOUBLE PRIMARY KEY," +
                    "    nombre VARCHAR(100) NOT NULL," +
                    "    apellidos VARCHAR(100) NOT NULL," +
                    "    email VARCHAR(200) NOT NULL," +
                    "    tipo_contrato VARCHAR(50) NOT NULL" +
                    ")");
            statement.execute("CREATE TABLE IF NOT EXISTS curso (" +
                    "    id DOUBLE PRIMARY KEY," +
                    "    nombre VARCHAR(100) NOT NULL," +
                    "    programa_id DOUBLE," +
                    "    activo BOOLEAN NOT NULL" +
                    ")");
            statement.execute("CREATE TABLE IF NOT EXISTS estudiante (" +
                    "    id DOUBLE PRIMARY KEY," +
                    "    nombre VARCHAR(100) NOT NULL," +
                    "    apellidos VARCHAR(100) NOT NULL," +
                    "    email VARCHAR(200) NOT NULL," +
                    "    codigo DOUBLE NOT NULL," +
                    "    programa_id DOUBLE," +
                    "    activo BOOLEAN NOT NULL," +
                    "    promedio DOUBLE" +
                    ")");
            statement.execute("CREATE TABLE IF NOT EXISTS inscripcion(" +
                    "    id DOUBLE PRIMARY KEY," +
                    "    curso_id DOUBLE NOT NULL," +
                    "    ano INT NOT NULL," +
                    "    semestre INT NOT NULL," +
                    "    estudiante_id DOUBLE NOT NULL," +
                    "    CONSTRAINT fk_inscripcion_curso FOREIGN KEY (curso_id) REFERENCES curso(id)," +
                    "    CONSTRAINT fk_inscripcion_estudiante FOREIGN KEY (estudiante_id) REFERENCES estudiante(id)" +
                    ")");

            statement.execute("INSERT IGNORE INTO curso (id, nombre, programa_id, activo) VALUES" +
                    "(101, 'Matemáticas I', 1, TRUE)," +
                    "(102, 'Física General', 1, TRUE)," +
                    "(103, 'Programación Orientada a Objetos', 1, TRUE)," +
                    "(104, 'Bases de Datos', 1, TRUE)," +
                    "(105, 'Historia de la Ingeniería', 2, FALSE)");
            statement.execute(
                    "INSERT IGNORE INTO estudiante (id, nombre, apellidos, email, codigo, programa_id, activo, promedio) VALUES"
                            +
                            "(1001, 'Jose', 'Alvarez', 'jose.alvarez@uni.edu', 2023001, 1, TRUE, 4.2)," +
                            "(1002, 'Santiago', 'Perez', 'santiago.perez@uni.edu', 2023002, 1, TRUE, 3.8)," +
                            "(1003, 'Maria', 'Lopez', 'maria.lopez@uni.edu', 2023003, 1, TRUE, 4.5)," +
                            "(1004, 'Camila', 'Rodriguez', 'camila.rodriguez@uni.edu', 2023004, 2, TRUE, 3.9)," +
                            "(1005, 'Daniel', 'Gonzalez', 'daniel.gonzalez@uni.edu', 2023005, 2, FALSE, 2.7)");
            statement.execute("INSERT IGNORE INTO inscripcion (id, curso_id, ano, semestre, estudiante_id) VALUES" +
                    "(5001, 101, 2024, 1, 1001)," +
                    "(5002, 102, 2024, 1, 1002)," +
                    "(5003, 103, 2024, 2, 1003)," +
                    "(5004, 104, 2025, 1, 1001)," +
                    "(5005, 105, 2023, 2, 1004)," +
                    "(5006, 101, 2025, 1, 1005);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConexion() {
        return this.connection;
    }

    @Override
    public String getCurrentDateTimeQuery() {
        return "SELECT NOW()";
    }
}
