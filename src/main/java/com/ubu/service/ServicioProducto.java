package com.ubu.service;

import java.util.List;
import com.ubu.modelo.Producto;

public interface ServicioProducto {
    List<Producto> getAllProductos();
    Producto getProductoById(Long id);
    Producto saveProducto(Producto producto);
    void deleteProducto(Long id);
}

