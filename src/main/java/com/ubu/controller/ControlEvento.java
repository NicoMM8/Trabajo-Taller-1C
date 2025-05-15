package com.ubu.controller;

import com.ubu.modelo.Evento;
import com.ubu.service.ServicioEvento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.springframework.validation.BindingResult;


@Controller
@RequestMapping("/eventos")
public class ControlEvento {

    private final ServicioEvento servicioEvento;
    private static final Logger logger = LoggerFactory.getLogger(ControlEvento.class);

    // Inyección por constructor (no es necesario anotar el campo si se utiliza la inyección por constructor)
    public ControlEvento(ServicioEvento servicioEvento) {
        this.servicioEvento = servicioEvento;
    }

    // Listar todos los eventos
    @GetMapping
    public String listEventos(Model model) {
        logger.info("Listando eventos");
        model.addAttribute("eventos", servicioEvento.getAllEventos());
        return "evento/list";  // Correspondiente a templates/evento/list.html
    }

    // Mostrar formulario para un nuevo evento
    @GetMapping("/nuevo")
    public String newEventoForm(Model model) {
        model.addAttribute("evento", new Evento());
        return "evento/form"; // Templates/evento/form.html
    }

    // Guardar o actualizar un evento
    @PostMapping("/guardar")
    public String saveEvento(@Valid @ModelAttribute("evento") Evento evento, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // Si hay errores de validación se regresa al formulario
            return "evento/form";
        }
        servicioEvento.saveEvento(evento);
        return "redirect:/eventos";
    }

    // Mostrar el formulario para editar un evento existente
    @GetMapping("/editar/{id}")
    public String editEvento(@PathVariable("id") Long id, Model model) {
        Evento evento = servicioEvento.getEventoById(id);
        model.addAttribute("evento", evento);
        return "evento/form";  // Reutiliza el mismo formulario
    }

    // Eliminar un evento
    @GetMapping("/eliminar/{id}")
    public String deleteEvento(@PathVariable("id") Long id) {
        servicioEvento.deleteEvento(id);
        return "redirect:/eventos";
    }
}

