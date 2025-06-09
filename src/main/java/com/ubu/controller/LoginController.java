package com.ubu.controller;

import com.ubu.modelo.Usuario;
import com.ubu.service.ServicioUsuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controlador para la gestión del login y registro de usuarios.
 * <p>
 * Este controlador se encarga de mostrar las vistas de login y registro, así como de procesar
 * el formulario de registro validando, por ejemplo, que las contraseñas coincidan.
 * </p>
 * 
 * Se utiliza inyección de dependencias a través del constructor para gestionar el servicio de usuario.
 * 
 * @author Nicolás Muñoz Miguel
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final ServicioUsuario servicioUsuario;

    /**
     * Constructor para inyectar el servicio de usuario.
     *
     * @param servicioUsuario Servicio para gestionar las operaciones relacionadas con los usuarios.
     */
    public LoginController(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    /**
     * Mapea la URL "/login" a la plantilla "login.html".
     *
     * @return Nombre de la plantilla de login.
     */
    @GetMapping("/login")
    public String login() {
        return "login"; // Renderiza src/main/resources/templates/login.html
    }

    /**
     * Mapea la URL "/registro" (método GET) a la plantilla "registro.html".
     * <p>
     * Se muestra el formulario de registro para que un nuevo usuario se registre.
     * </p>
     *
     * @param model Objeto Model para pasar atributos a la vista.
     * @return Nombre de la plantilla de registro.
     */
    @GetMapping("/registro")
    public String registro(Model model) {
        // Se agrega un objeto Usuario vacío al modelo para vincular el formulario
        model.addAttribute("usuario", new Usuario());
        return "registro"; // Renderiza src/main/resources/templates/registro.html
    }

    /**
     * Procesa el formulario de registro (método POST).
     * <p>
     * Valida que las contraseñas del usuario coincidan y, en caso de encontrarse errores de validación,
     * regresa el formulario con los mensajes correspondientes. Si la validación es exitosa, se guarda el usuario
     * y se redirige al login.
     * </p>
     *
     * @param usuario Objeto Usuario obtenido del formulario.
     * @param bindingResult Objeto BindingResult que contiene los errores de validación.
     * @return Redirige a la página de login si el registro es correcto; de lo contrario, muestra nuevamente el formulario.
     */
    @PostMapping("/registro")
    public String registrarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult) {
        // Validar que las contraseñas coincidan
        if (usuario.getPassword() != null && usuario.getConfirmPassword() != null &&
            !usuario.getPassword().equals(usuario.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.usuario", "Las contraseñas no coinciden");
        }

        // Si hay errores de validación, se regresa al formulario de registro
        if (bindingResult.hasErrors()) {
            return "registro";
        }

        // Asignar rol por defecto si no se selecciona (por ejemplo, USER)
        if (usuario.getRol() == null) {
            usuario.setRol(com.ubu.modelo.UserRole.USER);
        }

        // Guarda el usuario
        servicioUsuario.saveUser(usuario);
        logger.info("Usuario registrado exitosamente: {}", usuario.getUsername());

        // Redirigir a la página de login tras un registro exitoso
        return "redirect:/login";
    }
}



