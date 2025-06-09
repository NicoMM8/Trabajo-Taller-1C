package com.ubu.exception;

/**
 * Excepción lanzada cuando no se encuentra un evento solicitado.
 * <p>
 * Extiende de RuntimeException para permitir lanzamientos sin necesidad de declaración explícita.
 * </p>
 * 
 * @author Nicolás Muñoz Miguel
 */
public class EventoNotFoundException extends RuntimeException {
    
    /**
     * Constructor que permite especificar un mensaje descriptivo.
     *
     * @param message Mensaje descriptivo del error.
     */
    public EventoNotFoundException(String message) {
        super(message);
    }
}


