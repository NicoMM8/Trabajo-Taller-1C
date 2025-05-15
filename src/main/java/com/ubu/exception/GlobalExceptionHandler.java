package com.ubu.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Manejador para errores generales
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception ex, Model model) {
        logger.error("Ocurrió un error inesperado: ", ex);
        // Mensaje genérico para el usuario
        model.addAttribute("errorMessage", "Ha ocurrido un error inesperado. Por favor, inténtalo más tarde.");
        return "error"; // Redirige a templates/error.html
    }

    // Manejador para recursos no encontrados
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFound(ResourceNotFoundException ex, Model model) {
        logger.warn("Recurso no encontrado: {}", ex.getMessage());
        model.addAttribute("errorMessage", "El recurso solicitado no fue encontrado.");
        return "error-404"; // Redirige a templates/error-404.html
    }

    // Manejador para argumentos ilegales (ejemplo para error 400)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        logger.warn("Argumento inválido: {}", ex.getMessage());
        model.addAttribute("errorMessage", ex.getMessage());
        return "error-400"; // Redirige a templates/error-400.html
    }

    // Puedes agregar otros manejadores específicos según lo requiera la aplicación
}


