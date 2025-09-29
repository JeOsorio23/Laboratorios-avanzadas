package com.trabajo;

import javax.swing.SwingUtilities;

import com.trabajo.Conexion.ConexionDB;
import com.trabajo.Factories.DAOFactory;
import com.trabajo.Factories.MainDAOFactory;
import com.trabajo.Interfaz.InterfazMain;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConexionDB conexion = new ConexionDB();
            DAOFactory factory = MainDAOFactory.getFactory(conexion);

            new InterfazMain(factory, conexion);
        });
    }
}