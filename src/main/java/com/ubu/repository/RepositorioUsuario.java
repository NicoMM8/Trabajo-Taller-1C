package com.ubu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ubu.modelo.Usuario;

import java.util.Optional;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}

