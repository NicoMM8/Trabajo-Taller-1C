package com.ubu.controller;
import com.ubu.modelo.Usuario;
import com.ubu.service.ServicioUsuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class ControlUsuarios {

    private final ServicioUsuario servicioUsuario;
    private static final Logger logger = LoggerFactory.getLogger(ControlUsuarios.class);

    // Inyección por constructor
    public ControlUsuarios(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    // Listar todos los usuarios
    @GetMapping
    public String listUsuarios(Model model) {
        logger.info("Listando usuarios");
        model.addAttribute("usuarios", servicioUsuario.getAllUsers());
        return "usuario/list";  // Vista en templates/usuario/list.html
    }

    // Mostrar formulario para un nuevo usuario
    @GetMapping("/nuevo")
    public String newUsuarioForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/form";  // Vista en templates/usuario/form.html
    }

    // Procesar el guardado de usuario (para crear o actualizar)
    @PostMapping("/guardar")
    public String saveUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "usuario/form";  // Se reenvía al formulario si hay errores de validación.
        }
        servicioUsuario.saveUser(usuario);
        return "redirect:/usuarios";
    }

    // Mostrar formulario para editar un usuario existente
    @GetMapping("/editar/{id}")
    public String editUsuario(@PathVariable("id") Long id, Model model) {
        Usuario usuario = servicioUsuario.getUserById(id);
        model.addAttribute("usuario", usuario);
        return "usuario/form";  // Reutilizamos el mismo formulario.
    }

    // Eliminar un usuario
    @GetMapping("/eliminar/{id}")
    public String deleteUsuario(@PathVariable("id") Long id) {
        servicioUsuario.deleteUser(id);
        return "redirect:/usuarios";
    }
}

