package com.trabajo.Factories;

import com.trabajo.Conexion.ConexionDB;
import com.trabajo.DAO.CursoDAO;
import com.trabajo.DAO.CursosInscritosDAO;
import com.trabajo.DAO.EstudianteDAO;

public class H2DAOFactory implements DAOFactory{
    private final ConexionDB conexionDB;
    private static H2DAOFactory instancia;

    private H2DAOFactory(ConexionDB conexionDB){
        this.conexionDB = conexionDB;
    }

    public static H2DAOFactory getInstancia(ConexionDB conexionDB){
        if(instancia == null){
            synchronized(H2DAOFactory.class){
                if(instancia == null){
                    instancia = new H2DAOFactory(conexionDB);
                }
            }
        }
        return instancia;
    }

    @Override
    public CursoDAO createCursoDAO(ConexionDB conexion) {
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
