package com.trabajo.Interfaz;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.trabajo.DAO.ObserverDAO;

public class ConsolaObserver extends Thread implements ObserverDAO { 
    
    private final BlockingQueue<String> logMessages = new LinkedBlockingQueue<>();
    private volatile boolean running = true;
    private final String nombreConsola;

    public ConsolaObserver(String nombreConsola) {
        this.nombreConsola = nombreConsola;
        
        setDaemon(true); 
    }

    @Override
    public void notificarCambio(String mensaje) {
        try {
            logMessages.put(mensaje); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        System.out.println("\n--- CONSOLA DE REGISTRO DE CAMBIOS (" + nombreConsola + ") INICIADA ---");
        while (running) {
            try {
                String mensaje = logMessages.take(); 
                
                System.out.println("[LOG-" + nombreConsola + "] " + mensaje);
                
            } catch (InterruptedException e) {

                running = false;
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("--- CONSOLA DE REGISTRO DE CAMBIOS FINALIZADA ---");
    }

    public void detener() {
        running = false;

        interrupt(); 
    }
}