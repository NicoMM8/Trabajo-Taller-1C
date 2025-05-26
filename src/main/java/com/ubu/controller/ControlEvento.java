package com.ubu.controller;

import com.ubu.exception.EventoNotFoundException;
import com.ubu.modelo.Evento;
import com.ubu.service.ServicioEvento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/eventos")
public class ControlEvento {

    private final ServicioEvento servicioEvento;
    private static final Logger logger = LoggerFactory.getLogger(ControlEvento.class);

    // Inyección por constructor
    public ControlEvento(ServicioEvento servicioEvento) {
        this.servicioEvento = servicioEvento;
    }

    // Listar todos los eventos
    @GetMapping
    public String listEventos(Model model) {
        logger.info("Listando eventos");
        model.addAttribute("eventos", servicioEvento.getAllEventos());
        return "evento/list";  // Corresponde a templates/evento/list.html
    }

    // Mostrar formulario para un nuevo evento
    @GetMapping("/nuevo")
    public String newEventoForm(Model model) {
        model.addAttribute("evento", new Evento());
        return "evento/form"; // Corresponde a templates/evento/form.html
    }

    // Guardar o actualizar un evento
    @PostMapping("/guardar")
    public String saveEvento(@Valid @ModelAttribute Evento evento,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Si hay errores de validación, se regresa al formulario.
            // En la vista, se recomienda utilizar 'th:errors' para mostrar estos mensajes.
            return "evento/form";
        }
        servicioEvento.saveEvento(evento);
        return "redirect:/eventos";
    }

    // Mostrar el formulario para editar un evento existente
    @GetMapping("/editar/{id}")
    public String editEvento(@PathVariable Long id, Model model) {
        // Control de seguridad adicional: verificar si el usuario tiene permisos para editar el evento.
        // Esta validación se podría trasladar al nivel de servicio o combinar con anotaciones de seguridad.
        Evento evento = servicioEvento.getEventoById(id);
        if (evento == null) {
            // Si el evento no se encuentra, lanza una excepción para que el controlador global lo gestione.
            throw new EventoNotFoundException("El evento con id " + id + " no fue encontrado.");
        }
        model.addAttribute("evento", evento);
        return "evento/form";  // Se reutiliza el mismo formulario para edición
    }

    // Eliminar un evento
    // Nota: Es recomendable usar @PostMapping para operaciones que modifican el estado.
    @GetMapping("/eliminar/{id}")
    public String deleteEvento(@PathVariable Long id) {
        // Control de seguridad: verificar permisos adicionales en el servicio antes de la eliminación.
        servicioEvento.deleteEvento(id);
        return "redirect:/eventos";
    }
}


