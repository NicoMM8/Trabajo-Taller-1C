package com.ubu.controller;

import com.ubu.exception.EventoNotFoundException;
import com.ubu.modelo.Evento;
import com.ubu.modelo.Usuario;
import com.ubu.service.ServicioEvento;
import com.ubu.service.ServicioUsuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

/**
 * Controlador para operaciones CRUD sobre la entidad Evento.
 * Gestiona la navegación entre vistas y la lógica de negocio relacionada con eventos.
 * 
 * @author Nicolás Muñoz Miguel
 */
@Controller
@RequestMapping("/eventos")
public class ControlEvento {
    private final ServicioEvento servicioEvento;
    private final ServicioUsuario servicioUsuario;
    private static final Logger logger = LoggerFactory.getLogger(ControlEvento.class);

    /**
     * Inyección de dependencias de servicios de eventos y usuarios.
     */
    public ControlEvento(ServicioEvento servicioEvento, ServicioUsuario servicioUsuario) {
        this.servicioEvento = servicioEvento;
        this.servicioUsuario = servicioUsuario;
    }

    /**
     * Muestra la lista de todos los eventos.
     */
    @GetMapping
    public String listEventos(Model model) {
        logger.info("Listando eventos");
        model.addAttribute("eventos", servicioEvento.getAllEventos());
        return "evento/list";
    }

    /**
     * Muestra el formulario para crear un nuevo evento.
     */
    @GetMapping("/nuevo")
    public String newEventoForm(Model model) {
        model.addAttribute("evento", new Evento());
        return "evento/form";
    }

    /**
     * Guarda un evento nuevo o actualizado.
     * Si hay errores de validación, vuelve al formulario.
     */
    @PostMapping("/guardar")
    public String saveEvento(@Valid @ModelAttribute Evento evento, BindingResult bindingResult, Principal principal) {
        if (evento.getLatitud() == null || evento.getLongitud() == null) {
            bindingResult.rejectValue("ubicacion", "error.evento", "Debes seleccionar una ubicación válida del autocompletado.");
        }
        if (bindingResult.hasErrors()) {
            return "evento/form";
        }
        // Asigna el organizador si no está definido
        if (evento.getOrganizador() == null || evento.getOrganizador().getId() == null) {
            Usuario organizador = servicioUsuario.getUserByUsername(principal.getName());
            evento.setOrganizador(organizador);
        }
        servicioEvento.saveEvento(evento);
        return "redirect:/eventos";
    }

    /**
     * Muestra el formulario para editar un evento existente.
     * Lanza excepción si el evento no existe.
     */
    @GetMapping("/editar/{id}")
    public String editEvento(@PathVariable Long id, Model model) {
        Evento evento = servicioEvento.getEventoById(id);
        if (evento == null) {
            throw new EventoNotFoundException("El evento con id " + id + " no fue encontrado.");
        }
        model.addAttribute("evento", evento);
        return "evento/form";
    }

    /**
     * Elimina un evento por su ID.
     */
    @PostMapping("/eliminar/{id}")
    public String deleteEvento(@PathVariable Long id) {
        logger.info("Eliminando evento con id {}", id);
        try {
            servicioEvento.deleteEvento(id);
        } catch (Exception e) {
            logger.error("Error eliminando evento con id {}: {}", id, e.getMessage());
        }
        return "redirect:/eventos";
    }

    /**
     * Muestra la vista para explorar eventos.
     */
    @GetMapping("/explorar")
    public String explorarEventos(Model model) {
        model.addAttribute("eventos", servicioEvento.getAllEventos());
        return "evento/explorar";
    }

    /**
     * Muestra el detalle de un evento.
     */
    @GetMapping("/{id}")
    public String verDetalleEvento(@PathVariable Long id, Model model) {
        Evento evento = servicioEvento.getEventoById(id);
        model.addAttribute("evento", evento);
        return "evento/detalle";
    }

    /**
     * Configura el binder para convertir cadenas a LocalDateTime.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String value) {
                setValue(LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
            }
        });
    }
}
