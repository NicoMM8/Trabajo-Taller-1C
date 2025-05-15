package com.ubu.repository;

import com.ubu.modelo.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepositorio extends JpaRepository<Evento, Long> {
}

