package com.ubu.controller;
import com.ubu.modelo.Usuario;
import com.ubu.service.ServicioUsuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.security.Principal;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;


/**
 * Controlador para la gestión de Usuarios.
 * <p>
 * Esta clase administra los endpoints para listar, crear, editar y eliminar usuarios,
 * además de gestionar el registro de nuevos usuarios.
 * </p>
 * 
 * @author Nicolás Muñoz Miguel
 */
@Controller
@RequestMapping("/usuarios")
public class ControlUsuarios {

    private final ServicioUsuario servicioUsuario;
    private static final Logger logger = LoggerFactory.getLogger(ControlUsuarios.class);

    /**
     * Constructor con inyección de dependencias para el servicio de usuarios.
     *
     * @param servicioUsuario Servicio para gestionar operaciones relacionadas con usuarios.
     */
    public ControlUsuarios(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    /**
     * Lista todos los usuarios.
     *
     * @param model Objeto Model para pasar atributos a la vista.
     * @return Vista "user/list" que muestra la lista completa de usuarios.
     */
    @GetMapping
    public String listUsuarios(Model model) {
        logger.info("Listando usuarios");
        model.addAttribute("usuarios", servicioUsuario.getAllUsers());
        return "user/list";  // Vista: templates/usuario/list.html
    }

    /**
     * Muestra el formulario para crear un nuevo usuario.
     *
     * @param model Objeto Model para pasar atributos a la vista.
     * @return Vista "user/form" para la creación de un usuario.
     */
    @GetMapping("/nuevo")
    public String newUsuarioForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "user/form";  // Vista: templates/usuario/form.html
    }

    /**
     * Muestra el formulario de registro para un usuario nuevo.
     *
     * @param model Objeto Model para pasar atributos a la vista.
     * @return Vista "registro", ubicada en src/main/resources/templates/registro.html.
     */
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";  // Vista: templates/registro.html
    }

    /**
     * Procesa el guardado de un usuario, ya sea para crearlo o actualizarlo.
     * <p>
     * Si existen errores de validación, se reenvía al formulario.
     * </p>
     *
     * @param usuario        Objeto Usuario recibido del formulario.
     * @param bindingResult  Resultado de la validación de @Valid.
     * @return Redirecciona a la lista de usuarios si la operación resulta exitosa;
     *         en caso contrario, vuelve a mostrarse el formulario.
     */
    @PostMapping("/guardar")
    public String saveUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult bindingResult, Model model) {
        // Validación de duplicados
        Usuario existentePorUsername = null;
        Usuario existentePorEmail = null;
        try {
            existentePorUsername = servicioUsuario.getUserByUsername(usuario.getUsername());
        } catch (Exception ignored) {}
        try {
            existentePorEmail = servicioUsuario.getAllUsers().stream()
                .filter(u -> u.getEmail().equals(usuario.getEmail()))
                .findFirst().orElse(null);
        } catch (Exception ignored) {}

        if (usuario.getId() == null) { // Nuevo usuario
            if (existentePorUsername != null) {
                bindingResult.rejectValue("username", "error.usuario", "El nombre de usuario ya está en uso");
            }
            if (existentePorEmail != null) {
                bindingResult.rejectValue("email", "error.usuario", "El email ya está en uso");
            }
        } else { // Edición
            if (existentePorUsername != null && !existentePorUsername.getId().equals(usuario.getId())) {
                bindingResult.rejectValue("username", "error.usuario", "El nombre de usuario ya está en uso");
            }
            if (existentePorEmail != null && !existentePorEmail.getId().equals(usuario.getId())) {
                bindingResult.rejectValue("email", "error.usuario", "El email ya está en uso");
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("usuario", usuario);
            return "user/form";
        }

        // Si es edición y la contraseña está vacía, mantenla igual
        if (usuario.getId() != null && (usuario.getPassword() == null || usuario.getPassword().isBlank())) {
            Usuario existente = servicioUsuario.getUserById(usuario.getId());
            usuario.setPassword(existente.getPassword());
        }
        servicioUsuario.saveUser(usuario);
        return "redirect:/usuarios";
    }

    /**
     * Muestra el formulario para editar un usuario existente.
     *
     * @param id    Identificador del usuario a editar.
     * @param model Objeto Model para pasar atributos a la vista.
     * @return Vista "user/form" con los datos del usuario a editar.
     */
    @GetMapping("/editar/{id}")
    public String editUsuario(@PathVariable Long id, Principal principal, Model model) {
        Usuario usuario = servicioUsuario.getUserById(id);
        if (!usuario.getUsername().equals(principal.getName()) && !esAdmin(principal)) {
            throw new AccessDeniedException("No tienes permiso para editar este usuario.");
        }
        model.addAttribute("usuario", usuario);
        return "user/form";
    }

    /**
     * Elimina un usuario.
     *
     * @param id Identificador del usuario a eliminar.
     * @return Redirecciona a la lista de usuarios después de la eliminación.
     */
    @PostMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUsuario(@PathVariable Long id) {
        logger.info("Eliminando usuario con id {}", id);
        try {
            servicioUsuario.deleteUser(id);
        } catch (Exception e) {
            logger.error("Error eliminando usuario con id {}: {}", id, e.getMessage());
        }
        return "redirect:/usuarios";
    }

    /**
     * Comprueba si el usuario autenticado tiene el rol ADMIN.
     *
     * @param principal Objeto Principal que representa al usuario autenticado.
     * @return true si el usuario tiene rol ADMIN, false en caso contrario.
     */
    private boolean esAdmin(Principal principal) {
        if (principal == null) return false;
        try {
            Usuario usuario = servicioUsuario.getUserByUsername(principal.getName());
            return usuario.getRol() != null && usuario.getRol().name().equalsIgnoreCase("ADMIN");
        } catch (Exception e) {
            return false;
        }
    }
}
