package com.trabajo.Factories;

import com.trabajo.Conexion.ConexionDB;
import com.trabajo.DAO.CursoDAO;
import com.trabajo.DAO.CursosInscritosDAO;
import com.trabajo.DAO.EstudianteDAO;

public class MySQLDAOFactory implements DAOFactory{
    private final ConexionDB conexionDB;
    private static MySQLDAOFactory instancia;

    private MySQLDAOFactory(ConexionDB conexionDB){
        this.conexionDB = conexionDB;
    }

    public static MySQLDAOFactory getInstancia(ConexionDB conexionDB){
        if(instancia == null){
            synchronized(MySQLDAOFactory.class){
                if(instancia == null){
                    instancia = new MySQLDAOFactory(conexionDB);
                }
            }
        }
        return instancia;
    }

    @Override
    public CursoDAO createCursoDAO(ConexionDB conexionDB) {
        return CursoDAO.getInstancia(conexionDB);
    }

    @Override
    public EstudianteDAO createEstudianteDAO(ConexionDB conexionDB) {
        return EstudianteDAO.getInstancia(conexionDB);
    }

    @Override
    public CursosInscritosDAO createInscripcionesDAO(ConexionDB conexionDB) {
        CursoDAO cursoDAO = createCursoDAO(conexionDB);
        EstudianteDAO estudianteDAO = createEstudianteDAO(conexionDB);

        return CursosInscritosDAO.getInstancia(conexionDB, cursoDAO, estudianteDAO);
    }
}
