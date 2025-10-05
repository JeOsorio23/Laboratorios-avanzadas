package com.trabajo;

import javax.swing.SwingUtilities;

import com.trabajo.Conexion.ConexionDB;
import com.trabajo.Controller.CursosInscritosController;
import com.trabajo.DAO.CursoDAO;
import com.trabajo.DAO.CursosInscritosDAO;
import com.trabajo.DAO.EstudianteDAO;
import com.trabajo.Factories.DAOFactory;
import com.trabajo.Factories.MainDAOFactory;
import com.trabajo.Interfaz.ConsolaObserver;
import com.trabajo.Interfaz.InterfazMain;

public class Main {
    public static void main(String[] args) {
        ConsolaObserver observer = new ConsolaObserver("DAO_AUDITORIA");
        Thread observerThread = new Thread(observer, "DAOObserver_Thread");
        observerThread.start(); 

        SwingUtilities.invokeLater(() -> {
            ConexionDB conexion = new ConexionDB();
            DAOFactory factory = MainDAOFactory.getFactory(conexion);

            InterfazMain interfazMain = new InterfazMain(factory, conexion);
            CursoDAO cursoDAO = factory.createCursoDAO(conexion);
            EstudianteDAO estudianteDAO = factory.createEstudianteDAO(conexion);
            CursosInscritosDAO inscripcionDAO = factory.createInscripcionesDAO(conexion);

            cursoDAO.agregarObserver(observer);
            estudianteDAO.agregarObserver(observer);
            inscripcionDAO.agregarObserver(observer);
            
            CursosInscritosController.getInstancia(interfazMain, inscripcionDAO);
            Runtime.getRuntime().addShutdownHook(new Thread(observer::detener));
        });
    }
}