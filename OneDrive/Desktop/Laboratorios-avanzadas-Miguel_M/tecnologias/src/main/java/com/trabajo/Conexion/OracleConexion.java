package com.trabajo.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleConexion implements Conexion {
    private Connection connection;
    private static OracleConexion instancia;

    private OracleConexion() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            this.connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "1234");
            inicializarTablas();
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar o inicializar con Oracle: " + e.getMessage(), e);
        }
    }

    public static OracleConexion getInstancia(){
        if(instancia == null){
            synchronized(OracleConexion.class){
                if(instancia == null){
                    instancia = new OracleConexion();
                }
            }
        }
        return instancia;
    }

    private void inicializarTablas() {
        try (Statement statement = connection.createStatement()) {
            try {
                statement.execute("CREATE TABLE profesor (" +
                        "    id NUMBER PRIMARY KEY," +
                        "    nombre VARCHAR2(100) NOT NULL," +
                        "    apellidos VARCHAR2(100) NOT NULL," +
                        "    email VARCHAR2(200) NOT NULL," +
                        "    tipo_contrato VARCHAR2(50) NOT NULL" +
                        ")");
            } catch (SQLException e) {
                System.out.println("Tabla 'profesor' ya existe.");
            }

            try {
                statement.execute("CREATE TABLE curso (" +
                        "    id NUMBER PRIMARY KEY," +
                        "    nombre VARCHAR2(100) NOT NULL," +
                        "    programa_id NUMBER," +
                        "    activo NUMBER(1) NOT NULL" +
                        ")");
            } catch (SQLException e) {
                System.out.println("Tabla 'curso' ya existe.");
            }

            try {
                statement.execute("CREATE TABLE estudiante (" +
                        "    id NUMBER PRIMARY KEY," +
                        "    nombre VARCHAR2(100) NOT NULL," +
                        "    apellidos VARCHAR2(100) NOT NULL," +
                        "    email VARCHAR2(200) NOT NULL," +
                        "    codigo NUMBER NOT NULL," +
                        "    programa_id NUMBER," +
                        "    activo NUMBER(1) NOT NULL," +
                        "    promedio NUMBER" +
                        ")");
            } catch (SQLException e) {
                System.out.println("Tabla 'estudiante' ya existe.");
            }

            try {
                statement.execute("CREATE TABLE inscripcion(" +
                        "    id NUMBER PRIMARY KEY," +
                        "    curso_id NUMBER NOT NULL," +
                        "    ano NUMBER NOT NULL," +
                        "    semestre NUMBER NOT NULL," +
                        "    estudiante_id NUMBER NOT NULL," +
                        "    CONSTRAINT fk_inscripcion_curso FOREIGN KEY (curso_id) REFERENCES curso(id)," +
                        "    CONSTRAINT fk_inscripcion_estudiante FOREIGN KEY (estudiante_id) REFERENCES estudiante(id)"
                        +
                        ")");
            } catch (SQLException e) {
                System.out.println("Tabla 'inscripcion' ya existe.");
            }

            statement.execute(
                    "MERGE INTO curso a USING (SELECT 101 id, 'Matemáticas I' nombre, 1 programa_id, 1 activo FROM DUAL) b ON (a.id = b.id) WHEN NOT MATCHED THEN INSERT (id, nombre, programa_id, activo) VALUES (b.id, b.nombre, b.programa_id, b.activo)");
            statement.execute(
                    "MERGE INTO curso a USING (SELECT 102 id, 'Física General' nombre, 1 programa_id, 1 activo FROM DUAL) b ON (a.id = b.id) WHEN NOT MATCHED THEN INSERT (id, nombre, programa_id, activo) VALUES (b.id, b.nombre, b.programa_id, b.activo)");
            statement.execute(
                    "MERGE INTO curso a USING (SELECT 103 id, 'Programación Orientada a Objetos' nombre, 1 programa_id, 1 activo FROM DUAL) b ON (a.id = b.id) WHEN NOT MATCHED THEN INSERT (id, nombre, programa_id, activo) VALUES (b.id, b.nombre, b.programa_id, b.activo)");
            statement.execute(
                    "MERGE INTO curso a USING (SELECT 104 id, 'Bases de Datos' nombre, 1 programa_id, 1 activo FROM DUAL) b ON (a.id = b.id) WHEN NOT MATCHED THEN INSERT (id, nombre, programa_id, activo) VALUES (b.id, b.nombre, b.programa_id, b.activo)");
            statement.execute(
                    "MERGE INTO curso a USING (SELECT 105 id, 'Historia de la Ingeniería' nombre, 2 programa_id, 0 activo FROM DUAL) b ON (a.id = b.id) WHEN NOT MATCHED THEN INSERT (id, nombre, programa_id, activo) VALUES (b.id, b.nombre, b.programa_id, b.activo)");

            statement.execute(
                    "MERGE INTO estudiante a USING (SELECT 1001 id, 'Jose' nombre, 'Alvarez' apellidos, 'jose.alvarez@uni.edu' email, 2023001 codigo, 1 programa_id, 1 activo, 4.2 promedio FROM DUAL) b ON (a.id = b.id) WHEN NOT MATCHED THEN INSERT (id, nombre, apellidos, email, codigo, programa_id, activo, promedio) VALUES (b.id, b.nombre, b.apellidos, b.email, b.codigo, b.programa_id, b.activo, b.promedio)");
            statement.execute(
                    "MERGE INTO estudiante a USING (SELECT 1002 id, 'Santiago' nombre, 'Perez' apellidos, 'santiago.perez@uni.edu' email, 2023002 codigo, 1 programa_id, 1 activo, 3.8 promedio FROM DUAL) b ON (a.id = b.id) WHEN NOT MATCHED THEN INSERT (id, nombre, apellidos, email, codigo, programa_id, activo, promedio) VALUES (b.id, b.nombre, b.apellidos, b.email, b.codigo, b.programa_id, b.activo, b.promedio)");
            statement.execute(
                    "MERGE INTO estudiante a USING (SELECT 1003 id, 'Maria' nombre, 'Lopez' apellidos, 'maria.lopez@uni.edu' email, 2023003 codigo, 1 programa_id, 1 activo, 4.5 promedio FROM DUAL) b ON (a.id = b.id) WHEN NOT MATCHED THEN INSERT (id, nombre, apellidos, email, codigo, programa_id, activo, promedio) VALUES (b.id, b.nombre, b.apellidos, b.email, b.codigo, b.programa_id, b.activo, b.promedio)");
            statement.execute(
                    "MERGE INTO estudiante a USING (SELECT 1004 id, 'Camila' nombre, 'Rodriguez' apellidos, 'camila.rodriguez@uni.edu' email, 2023004 codigo, 2 programa_id, 1 activo, 3.9 promedio FROM DUAL) b ON (a.id = b.id) WHEN NOT MATCHED THEN INSERT (id, nombre, apellidos, email, codigo, programa_id, activo, promedio) VALUES (b.id, b.nombre, b.apellidos, b.email, b.codigo, b.programa_id, b.activo, b.promedio)");
            statement.execute(
                    "MERGE INTO estudiante a USING (SELECT 1005 id, 'Daniel' nombre, 'Gonzalez' apellidos, 'daniel.gonzalez@uni.edu' email, 2023005 codigo, 2 programa_id, 0 activo, 2.7 promedio FROM DUAL) b ON (a.id = b.id) WHEN NOT MATCHED THEN INSERT (id, nombre, apellidos, email, codigo, programa_id, activo, promedio) VALUES (b.id, b.nombre, b.apellidos, b.email, b.codigo, b.programa_id, b.activo, b.promedio)");

            statement.execute(
                    "MERGE INTO inscripcion a USING (SELECT 5001 id, 101 curso_id, 2024 ano, 1 semestre, 1001 estudiante_id FROM DUAL) b ON (a.id = b.id) WHEN NOT MATCHED THEN INSERT (id, curso_id, ano, semestre, estudiante_id) VALUES (b.id, b.curso_id, b.ano, b.semestre, b.estudiante_id)");
            statement.execute(
                    "MERGE INTO inscripcion a USING (SELECT 5002 id, 102 curso_id, 2024 ano, 1 semestre, 1002 estudiante_id FROM DUAL) b ON (a.id = b.id) WHEN NOT MATCHED THEN INSERT (id, curso_id, ano, semestre, estudiante_id) VALUES (b.id, b.curso_id, b.ano, b.semestre, b.estudiante_id)");
            statement.execute(
                    "MERGE INTO inscripcion a USING (SELECT 5003 id, 103 curso_id, 2024 ano, 2 semestre, 1003 estudiante_id FROM DUAL) b ON (a.id = b.id) WHEN NOT MATCHED THEN INSERT (id, curso_id, ano, semestre, estudiante_id) VALUES (b.id, b.curso_id, b.ano, b.semestre, b.estudiante_id)");
            statement.execute(
                    "MERGE INTO inscripcion a USING (SELECT 5004 id, 104 curso_id, 2024 ano, 2 semestre, 1004 estudiante_id FROM DUAL) b ON (a.id = b.id) WHEN NOT MATCHED THEN INSERT (id, curso_id, ano, semestre, estudiante_id) VALUES (b.id, b.curso_id, b.ano, b.semestre, b.estudiante_id)");
            statement.execute(
                    "MERGE INTO inscripcion a USING (SELECT 5005 id, 104 curso_id, 2024 ano, 2 semestre, 1005 estudiante_id FROM DUAL) b ON (a.id = b.id) WHEN NOT MATCHED THEN INSERT (id, curso_id, ano, semestre, estudiante_id) VALUES (b.id, b.curso_id, b.ano, b.semestre, b.estudiante_id)");

        } catch (SQLException e) {
            throw new RuntimeException("Error al inicializar tablas en Oracle: " + e.getMessage(), e);
        }
    }

    @Override
    public Connection getConexion() {
        return this.connection;
    }

    @Override
    public String getCurrentDateTimeQuery() {
        return "SELECT SYSDATE FROM DUAL";
    }
}
