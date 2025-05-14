package com.ubu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.ubu.modelo.Producto;
import com.ubu.service.ServicioProducto;

@Controller
@RequestMapping("/productos")
public class ControlProducto {

    @Autowired
    private ServicioProducto servicioProducto;

    // Listar todos los productos
    @GetMapping
    public String listProductos(Model model) {
        model.addAttribute("productos", servicioProducto.getAllProductos());
        return "producto/list";  // Correspondiente a templates/producto/list.html
    }

    // Mostrar formulario para nuevo producto
    @GetMapping("/nuevo")
    public String newProductoForm(Model model) {
        model.addAttribute("producto", new Producto());
        return "producto/form"; // templates/producto/form.html
    }

    // Guardar o actualizar producto
    @PostMapping("/guardar")
    public String saveProducto(@ModelAttribute("producto") Producto producto) {
        servicioProducto.saveProducto(producto);
        return "redirect:/productos";
    }

    // Mostrar formulario para editar producto existente
    @GetMapping("/editar/{id}")
    public String editProducto(@PathVariable("id") Long id, Model model) {
        Producto producto = servicioProducto.getProductoById(id);
        model.addAttribute("producto", producto);
        return "producto/form";  // Reutiliza el formulario
    }

    // Eliminar producto
    @GetMapping("/eliminar/{id}")
    public String deleteProducto(@PathVariable("id") Long id) {
        servicioProducto.deleteProducto(id);
        return "redirect:/productos";
    }
}

