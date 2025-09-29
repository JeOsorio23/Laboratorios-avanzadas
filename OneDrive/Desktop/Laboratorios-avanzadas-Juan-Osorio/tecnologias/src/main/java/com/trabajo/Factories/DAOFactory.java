package com.trabajo.Factories;

import com.trabajo.Conexion.ConexionDB;
import com.trabajo.DAO.CursoDAO;
import com.trabajo.DAO.CursosInscritosDAO;
import com.trabajo.DAO.EstudianteDAO;

public interface DAOFactory {
    CursoDAO createCursoDAO(ConexionDB conexionDB);
    EstudianteDAO createEstudianteDAO(ConexionDB conexion);
    CursosInscritosDAO createInscripcionesDAO(ConexionDB conexionDB);
}
