package com.ubu.controller;

import com.ubu.dto.Category;
import com.ubu.modelo.Evento;
import com.ubu.service.ServicioEvento;
import com.ubu.dto.EventoMapaDTO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controlador para la página de inicio.
 * <p>
 * Este controlador se encarga de cargar y mostrar las categorías de eventos en la página principal.
 * </p>
 * 
 * @author  Nicolás Muñoz Miguel
 */
@Controller
public class HomeController {

    // Servicio para gestionar eventos
    private final ServicioEvento servicioEvento;

    /**
     * Constructor que inyecta el servicio de eventos.
     * 
     * @param servicioEvento Servicio para la gestión de eventos.
     */
    public HomeController(ServicioEvento servicioEvento) {
        this.servicioEvento = servicioEvento;
    }

    /**
     * Mapea la ruta raíz ("/") y carga la vista principal.
     *
     * @param model Objeto Model utilizado para pasar atributos a la vista.
     * @param logout Parámetro opcional para mostrar mensaje de cierre de sesión.
     * @return Nombre de la plantilla Thymeleaf a renderizar ("index").
     */
    @GetMapping("/")
    public String home(Model model, @RequestParam(value = "logout", required = false) String logout) {
        // Lista de categorías de eventos a mostrar en la página principal
        List<Category> categories = Arrays.asList(
            new Category(1, "Música en vivo", "Conciertos, jam sessions y presentaciones musicales", "<i class='bi bi-music-note-beamed fs-4 text-secondary'></i>"),
            new Category(2, "Arte y exposiciones", "Galerías, exhibiciones y talleres artísticos", "<i class='bi bi-palette fs-4 text-secondary'></i>"),
            new Category(3, "Gastronomía", "Festivales gastronómicos y experiencias culinarias", "<i class='bi bi-cup-hot fs-4 text-secondary'></i>"),
            new Category(4, "Cultura y tradiciones", "Eventos culturales y celebraciones tradicionales", "<i class='bi bi-building fs-4 text-secondary'></i>")
        );

        // Añade la lista de categorías al modelo para la vista
        model.addAttribute("categories", categories);

        // Obtiene la lista de eventos reales y la añade al modelo
        List<Evento> eventos = servicioEvento.getAllEventos();
        model.addAttribute("eventos", eventos);

        // Mapea los eventos a DTO para el mapa
        List<EventoMapaDTO> eventosMapa = eventos.stream()
            .map(e -> new EventoMapaDTO(e.getId(), e.getNombre(), e.getLatitud(), e.getLongitud()))
            .collect(Collectors.toList());
        model.addAttribute("eventosMapa", eventosMapa);

        // Si el usuario ha cerrado sesión, añade un mensaje al modelo
        if (logout != null) {
            model.addAttribute("logoutMessage", "Sesión cerrada correctamente.");
        }
        // Devuelve el nombre de la plantilla a renderizar
        return "index";
    }
}


