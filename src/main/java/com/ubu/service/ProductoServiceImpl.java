package com.ubu.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ubu.modelo.Producto;
import com.ubu.repository.ProductoRepositorio;

@Service
public class ProductoServiceImpl implements ServicioProducto {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Override
    public List<Producto> getAllProductos() {
        return productoRepositorio.findAll();
    }

    @Override
    public Producto getProductoById(Long id) {
        return productoRepositorio.findById(id).orElse(null);
    }

    @Override
    public Producto saveProducto(Producto producto) {
        return productoRepositorio.save(producto);
    }

    @Override
    public void deleteProducto(Long id) {
        productoRepositorio.deleteById(id);
    }
}

