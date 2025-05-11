package com.ubu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // Mapea la URL /login a la plantilla login.html
    @GetMapping("/login")
    public String login() {
        return "login";  // Renderiza src/main/resources/templates/login.html
    }
}

