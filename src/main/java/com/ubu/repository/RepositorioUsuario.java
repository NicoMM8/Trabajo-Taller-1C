package com.ubu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ubu.modelo.Usuario;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {
    // Aquí podrías agregar consultas personalizadas si las necesitas
}

