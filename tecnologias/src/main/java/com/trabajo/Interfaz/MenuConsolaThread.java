package com.trabajo.Interfaz;

import com.trabajo.Conexion.ConexionDB;
import com.trabajo.Curso;
import com.trabajo.Estudiante;
import com.trabajo.DAO.CursoDAO;
import com.trabajo.DAO.EstudianteDAO;
import com.trabajo.Factories.DatabaseDateUtil;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class MenuConsolaThread extends Thread {

    private ConexionDB conexion;
    private EstudianteDAO estudianteDAO;
    private CursoDAO cursoDAO;
    private volatile boolean activo;
    private Scanner scanner;

    public MenuConsolaThread(ConexionDB conexion, EstudianteDAO estudianteDAO, CursoDAO cursoDAO) {
        this.conexion = conexion;
        this.estudianteDAO = estudianteDAO;
        this.cursoDAO = cursoDAO;
        this.activo = true;
        this.scanner = new Scanner(System.in);
        setDaemon(true); // El hilo se cierra cuando se cierra la aplicación
    }

    @Override
    public void run() {
        System.out.println("\n=== MENÚ DE CONSOLA INICIADO ===\n");

        while (activo) {
            mostrarMenu();

            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                procesarOpcion(opcion);
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un numero valido\n");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + "\n");
            }
        }

        scanner.close();
    }

    private void mostrarMenu() {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║          MENÚ PRINCIPAL            ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println("1. Mostrar fecha de BD");
        System.out.println("2. Listar estudiantes");
        System.out.println("3. Listar cursos");
        System.out.println("4. Salir");
        System.out.print("Opción: ");
    }

    private void procesarOpcion(int opcion) {
        System.out.println(); // Línea en blanco

        switch (opcion) {
            case 1:
                mostrarFechaBD();
                break;
            case 2:
                listarEstudiantes();
                break;
            case 3:
                listarCursos();
                break;
            case 4:
                salir();
                break;
            default:
                System.out.println("Opcion invalida. Intente de nuevo.\n");
        }
    }

    private void mostrarFechaBD() {
        try {
            Timestamp fechaBD = DatabaseDateUtil.getCurrentDateTime(
                    conexion.getConexion().getConexion());
            System.out.println("Fecha de la base de datos: " + fechaBD);
            System.out.println();
        } catch (Exception e) {
            System.out.println("Error al obtener fecha: " + e.getMessage() + "\n");
        }
    }

    private void listarEstudiantes() {
        try {
            List<Estudiante> estudiantes = estudianteDAO.obtenerTodos();

            if (estudiantes.isEmpty()) {
                System.out.println("No hay estudiantes registrados.\n");
                return;
            }

            System.out.println("LISTA DE ESTUDIANTES:");
            System.out.println("─────────────────────────────────────────");
            for (Estudiante est : estudiantes) {
                String programa = est.getPrograma() != null ? est.getPrograma().getNombre() : "Sin programa";
                String estado = est.isActivo() ? "Activo" : "Inactivo";
                System.out.printf(
                        "ID: %.0f | Codigo: %.0f | Nombre: %s %s | Promedio: %.2f | Estado: %s%n",
                        est.getId(),
                        est.getCodigo(),
                        est.getNombre(),
                        est.getApellidos(),
                        est.getPromedio(),
                        estado);
            }
            System.out.println("─────────────────────────────────────────");
            System.out.println("Total: " + estudiantes.size() + " estudiantes\n");

        } catch (Exception e) {
            System.out.println("Error al listar estudiantes: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private void listarCursos() {
        try {
            List<Curso> cursos = cursoDAO.obtenerTodos();

            if (cursos.isEmpty()) {
                System.out.println("No hay cursos registrados.\n");
                return;
            }

            System.out.println("LISTA DE CURSOS:");
            System.out.println("─────────────────────────────────────────");
            for (Curso c : cursos) {
                String programa = c.getPrograma() != null ? c.getPrograma().getNombre() : "Sin programa";
                String estado = c.isActivo() ? "Activo" : "Inactivo";
                System.out.printf("ID: %.0f | Nombre: %s %n",
                        c.getId(),
                        c.getNombre());
            }
            System.out.println("─────────────────────────────────────────");
            System.out.println("Total: " + cursos.size() + " cursos\n");

        } catch (Exception e) {
            System.out.println("Error al listar cursos: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
    }

    private void salir() {
        System.out.println("Cerrando menu de consola...");
        System.out.println("La GUI sigue activa.\n");
        activo = false;
    }

    public void detener() {
        activo = false;
    }
}