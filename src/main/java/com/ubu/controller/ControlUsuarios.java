package com.ubu.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.ubu.modelo.Usuario;
import com.ubu.service.ServicioUsuario;

@Controller
@RequestMapping("/usuarios")
public class ControlUsuarios {

    @Autowired
    private ServicioUsuario servicioUsuario;

    // Listar todos los usuarios
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("usuarios", servicioUsuario.getAllUsers());
        return "user/list";  // Corresponde a templates/user/list.html
    }

    // Mostrar formulario para un nuevo usuario
    @GetMapping("/nuevo")
    public String newUserForm(Model model) {
        model.addAttribute("usuario", new Usuario ());
        return "user/form";  // Corresponde a templates/user/form.html
    }

    // Procesar el guardado de usuario (para crear o actualizar)
    @PostMapping("/guardar")
    public String saveUser(@ModelAttribute("usuario") Usuario usuario) {
        servicioUsuario.saveUser(usuario);
        return "redirect:/usuarios";
    }

    // Mostrar formulario para editar un usuario existente
    @GetMapping("/editar/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        Usuario usuario = servicioUsuario.getUserById(id);
        model.addAttribute("usuario", usuario);
        return "user/form";  // Reutilizamos el mismo formulario
    }

    // Eliminar un usuario
    @GetMapping("/eliminar/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        servicioUsuario.deleteUser(id);
        return "redirect:/usuarios";
    }
}

