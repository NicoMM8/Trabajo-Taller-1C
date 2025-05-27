package com.ubu.controller;

import com.ubu.modelo.Usuario;
import com.ubu.service.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private ServicioUsuario servicioUsuario;

    // Mapea la URL /login a la plantilla login.html
    @GetMapping("/login")
    public String login() {
        return "login";  // Renderiza src/main/resources/templates/login.html
    }

    // Mapea la URL /registro a la plantilla registro.html (GET request)
    @GetMapping("/registro")
    public String registro() {
        // Devuelve el nombre de la plantilla para el formulario de registro
        return "registro";  // Renderiza src/main/resources/templates/registro.html
    }

    // Procesa el formulario de registro (POST request)
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult) {
        // Validar que las contraseñas coincidan
        if (usuario.getPassword() != null && usuario.getConfirmPassword() != null && !usuario.getPassword().equals(usuario.getConfirmPassword())) {
            // El primer argumento de rejectValue es el campo del formulario al que se asocia el error.
            // El segundo es un código de error (opcional, usado para internacionalización o mensajes genéricos).
            // El tercero es el mensaje de error por defecto.
            bindingResult.rejectValue("confirmPassword", "error.usuario", "Las contraseñas no coinciden");
        }

        // Si hay errores de validación (incluyendo el de las contraseñas o los definidos por anotaciones en Usuario),
        // se vuelve a mostrar el formulario de registro con los mensajes de error.
        // Spring Boot maneja automáticamente la validación de anotaciones como @NotBlank, @Email en el modelo Usuario
        // y añade esos errores a bindingResult antes de que este método sea llamado.
        if (bindingResult.hasErrors()) {
            return "registro"; // Muestra de nuevo la plantilla src/main/resources/templates/registro.html
        }

        // Si no hay errores, guardar el usuario.
        // La interfaz ServicioUsuario define "saveUser", así que usamos ese método.
        servicioUsuario.saveUser(usuario);
        
        // Redirigir a la página de login después de un registro exitoso.
        // "redirect:" evita que el usuario pueda reenviar el formulario con F5.
        return "redirect:/login";
    }
}

