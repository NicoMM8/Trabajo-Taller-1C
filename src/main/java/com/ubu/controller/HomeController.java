package com.ubu.controller;

import com.ubu.dto.Category;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        List<Category> categories = Arrays.asList(
                new Category(1, "Música en vivo", "Conciertos, jam sessions y presentaciones musicales", "<i class='bi bi-music-note-beamed fs-4 text-secondary'></i>"),
                new Category(2, "Arte y exposiciones", "Galerías, exhibiciones y talleres artísticos", "<i class='bi bi-palette fs-4 text-secondary'></i>"),
                new Category(3, "Gastronomía", "Festivales gastronómicos y experiencias culinarias", "<i class='bi bi-cup-hot fs-4 text-secondary'></i>"),
                new Category(4, "Cultura y tradiciones", "Eventos culturales y celebraciones tradicionales", "<i class='bi bi-building fs-4 text-secondary'></i>")
        );

        model.addAttribute("categories", categories);
        return "index";
    }
}

