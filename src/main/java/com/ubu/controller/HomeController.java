package com.ubu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // Renderiza la plantilla index.html ubicada en templates
        return "index";
    }
}
