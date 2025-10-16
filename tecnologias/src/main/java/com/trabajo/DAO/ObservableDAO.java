package com.trabajo.DAO;

import java.util.ArrayList;
import java.util.List;

public abstract class ObservableDAO {
    private List<ObserverDAO> observadores = new ArrayList<>();

    public void agregarObserver(ObserverDAO observer){
        observadores.add(observer);
    }

    public void eliminarObserver(ObserverDAO observer){
        observadores.add(observer);
    }

    public void notificarCambio(String operacion, String entidad, Object objetoAfectado){
        String mensaje = String.format("[%s] %s: %s", entidad, operacion, objetoAfectado);
        for(ObserverDAO observerDAO: observadores){
            observerDAO.notificarCambio(mensaje);
        }
    }
}
