package com.trabajo.Factories;

import com.trabajo.Conexion.ConexionDB;
import com.trabajo.DAO.CursoDAO;
import com.trabajo.DAO.CursosInscritosDAO;
import com.trabajo.DAO.EstudianteDAO;

public class H2DAOFactory implements DAOFactory{
    private final ConexionDB conexionDB;

    public H2DAOFactory(ConexionDB conexionDB){
        this.conexionDB = conexionDB;
    }

    @Override
    public CursoDAO createCursoDAO(ConexionDB conexion) {
        return new CursoDAO(conexionDB);
    }

    @Override
    public EstudianteDAO createEstudianteDAO(ConexionDB conexionDB) {
        return new EstudianteDAO(conexionDB);
    }

    @Override
    public CursosInscritosDAO createInscripcionesDAO(ConexionDB conexionDB) {
        CursoDAO cursoDAO = createCursoDAO(conexionDB);
        EstudianteDAO estudianteDAO = createEstudianteDAO(conexionDB);
        
        return new CursosInscritosDAO(conexionDB, cursoDAO, estudianteDAO);
    }
    
}
