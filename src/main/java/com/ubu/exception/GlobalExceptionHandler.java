package com.ubu.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Controlador global de excepciones para la aplicación.
 * <p>
 * Captura las excepciones lanzadas en los controladores y redirige a vistas específicas
 * que muestran mensajes de error amigables al usuario.
 * </p>
 * 
 * @author Nicolás Muñoz Miguel
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Maneja cualquier excepción no específica que ocurra en la aplicación.
     *
     * @param ex    La excepción capturada.
     * @param model Modelo para pasar atributos a la vista.
     * @return Nombre de la vista genérica de error ("error").
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception ex, Model model) {
        logger.error("Ocurrió un error inesperado: ", ex);
        model.addAttribute("errorMessage", "Ha ocurrido un error inesperado. Por favor, inténtalo más tarde.");
        return "error"; // Vista ubicada en: templates/error.html
    }

    /**
     * Maneja excepciones de recurso no encontrado.
     *
     * @param ex    La excepción ResourceNotFoundException capturada.
     * @param model Modelo para pasar atributos a la vista.
     * @return Nombre de la vista para error 404 ("error-404").
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFound(ResourceNotFoundException ex, Model model) {
        logger.warn("Recurso no encontrado: {}", ex.getMessage());
        model.addAttribute("errorMessage", "El recurso solicitado no fue encontrado.");
        return "error-404"; // Vista ubicada en: templates/error-404.html
    }

    /**
     * Maneja excepciones debidas a argumentos inválidos.
     *
     * @param ex    La excepción IllegalArgumentException capturada.
     * @param model Modelo para pasar atributos a la vista.
     * @return Nombre de la vista para error 400 ("error-400").
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        logger.warn("Argumento inválido: {}", ex.getMessage());
        model.addAttribute("errorMessage", ex.getMessage());
        return "error-400"; // Vista ubicada en: templates/error-400.html
    }

}



