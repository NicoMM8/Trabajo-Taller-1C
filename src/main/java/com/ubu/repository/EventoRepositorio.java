package com.ubu.repository;

import com.ubu.modelo.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para la entidad {@link Evento}.
 * <p>
 * Provee las operaciones CRUD estándar a través de la extensión de JpaRepository.
 * </p>
 * 
 * @author Nicolás Muñoz Miguel
 */
public interface EventoRepositorio extends JpaRepository<Evento, Long> {
}


