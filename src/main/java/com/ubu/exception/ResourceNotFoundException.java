package com.ubu.exception;

/**
 * Excepción lanzada cuando un recurso no es encontrado.
 * <p>
 * Se utiliza en casos generales donde un recurso solicitado no existe en la base de datos o en la lógica de negocio.
 * </p>
 * 
 * @author Nicolás Muñoz Miguel
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor que permite especificar un mensaje descriptivo cuando un recurso no se encuentra.
     *
     * @param message Mensaje descriptivo del error.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}


