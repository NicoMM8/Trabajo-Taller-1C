package com.ubu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ubu.modelo.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
}

