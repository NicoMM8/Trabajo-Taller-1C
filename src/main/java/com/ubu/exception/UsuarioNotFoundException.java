package com.ubu.exception;

/**
 * Excepción lanzada cuando un usuario no es encontrado.
 * <p>
 * Esta excepción se utiliza para distinguir errores relacionados con la búsqueda de usuarios inexistentes.
 * </p>
 * 
 * @author Nicolás Muñoz Miguel
 */
public class UsuarioNotFoundException extends RuntimeException {

    /**
     * Constructor que permite especificar un mensaje descriptivo al no encontrar un usuario.
     *
     * @param message Mensaje descriptivo del error.
     */
    public UsuarioNotFoundException(String message) {
        super(message);
    }
}


