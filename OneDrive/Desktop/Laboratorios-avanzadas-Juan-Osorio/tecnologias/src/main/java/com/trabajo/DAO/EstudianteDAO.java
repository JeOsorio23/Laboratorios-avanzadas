package com.trabajo.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.trabajo.Estudiante;
import com.trabajo.Programa;
import com.trabajo.Conexion.ConexionDB;

public class EstudianteDAO {
    private final ConexionDB conexionDB;

    public EstudianteDAO(ConexionDB conexionDB) {
        this.conexionDB = conexionDB;
    }

    /**
     * Inserta un nuevo estudiante en la base de datos.
     *
     * @param estudiante El objeto Estudiante a insertar.
     */
    public void insertar(Estudiante estudiante) {
        String sql = "INSERT INTO estudiante (id, nombre, apellidos, email, codigo, programa_id, activo, promedio) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conexionDB.getConexion().getConexion().prepareStatement(sql)) {
            pstmt.setDouble(1, estudiante.getId());
            pstmt.setString(2, estudiante.getNombre());
            pstmt.setString(3, estudiante.getApellidos());
            pstmt.setString(4, estudiante.getEmail());
            pstmt.setDouble(5, estudiante.getCodigo());

            // Si el programa existe, inserta su ID. De lo contrario, inserta null.
            if (estudiante.getPrograma() != null) {
                pstmt.setDouble(6, estudiante.getPrograma().getId());
            } else {
                pstmt.setNull(6, java.sql.Types.DOUBLE);
            }

            pstmt.setBoolean(7, estudiante.getActivo());
            pstmt.setDouble(8, estudiante.getPromedio());
            pstmt.executeUpdate();
            System.out.println("Estudiante insertado: " + estudiante.getNombre());
        } catch (SQLException e) {
            System.err.println("Error al insertar estudiante: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Recupera todos los estudiantes de la base de datos.
     *
     * @return Una lista de objetos Estudiante.
     */
    public List<Estudiante> obtenerTodos() {
        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT * FROM estudiante";
        try (Statement stmt = conexionDB.getConexion().getConexion().createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Estudiante estudiante = mapearEstudiante(rs);
                lista.add(estudiante);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los estudiantes: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Busca un estudiante por su ID.
     *
     * @param id El ID del estudiante a buscar.
     * @return El objeto Estudiante si se encuentra, de lo contrario, null.
     */
    public Estudiante buscarPorId(Double id) {
        Estudiante estudiante = null;
        String sql = "SELECT * FROM estudiante WHERE id = ?";
        try (PreparedStatement pstmt = conexionDB.getConexion().getConexion().prepareStatement(sql)) {
            pstmt.setDouble(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    estudiante = mapearEstudiante(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar estudiante por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return estudiante;
    }

    /**
     * Actualiza la información de un estudiante en la base de datos.
     *
     * @param estudiante El objeto Estudiante con la información actualizada.
     */
    public void actualizar(Estudiante estudiante) {
        String sql = "UPDATE estudiante SET nombre = ?, apellidos = ?, email = ?, codigo = ?, programa_id = ?, activo = ?, promedio = ? "
                +
                "WHERE id = ?";
        try (PreparedStatement pstmt = conexionDB.getConexion().getConexion().prepareStatement(sql)) {
            pstmt.setString(1, estudiante.getNombre());
            pstmt.setString(2, estudiante.getApellidos());
            pstmt.setString(3, estudiante.getEmail());
            pstmt.setDouble(4, estudiante.getCodigo());

            if (estudiante.getPrograma() != null) {
                pstmt.setDouble(5, estudiante.getPrograma().getId());
            } else {
                pstmt.setNull(5, java.sql.Types.DOUBLE);
            }

            pstmt.setBoolean(6, estudiante.getActivo());
            pstmt.setDouble(7, estudiante.getPromedio());
            pstmt.setDouble(8, estudiante.getId());
            pstmt.executeUpdate();
            System.out.println("Estudiante actualizado: " + estudiante.getNombre());
        } catch (SQLException e) {
            System.err.println("Error al actualizar estudiante: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Elimina un estudiante de la base de datos por su ID.
     *
     * @param id El ID del estudiante a eliminar.
     */
    public void eliminar(Double id) {
        String sql = "DELETE FROM estudiante WHERE id = ?";
        try (PreparedStatement pstmt = conexionDB.getConexion().getConexion().prepareStatement(sql)) {
            pstmt.setDouble(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Estudiante con ID " + id + " eliminado.");
            } else {
                System.out.println("No se encontró ningún estudiante con ID " + id + " para eliminar.");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar estudiante: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método auxiliar para mapear un ResultSet a un objeto Estudiante.
     * Esto evita la duplicación de código en los métodos de lectura.
     *
     * @param rs El ResultSet que contiene los datos del estudiante.
     * @return Un nuevo objeto Estudiante.
     * @throws SQLException Si ocurre un error al acceder a los datos.
     */
    private Estudiante mapearEstudiante(ResultSet rs) throws SQLException {
        Double id = rs.getDouble("id");
        String nombres = rs.getString("nombre");
        String apellidos = rs.getString("apellidos");
        String email = rs.getString("email");
        Double codigo = rs.getDouble("codigo");
        boolean activo = rs.getBoolean("activo");
        Double promedio = rs.getDouble("promedio");

        // Asume que la clase Programa ya existe y se puede instanciar o buscar.
        // Se puede mejorar este código para buscar el programa por su ID.
        Double programaId = rs.getDouble("programa_id");
        Programa programa = new Programa(programaId, null, null, null, null); // Ejemplo de instanciación

        return new Estudiante(id, nombres, apellidos, email, codigo, programa, activo, promedio);
    }
}