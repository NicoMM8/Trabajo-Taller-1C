package com.ubu.service;

import com.ubu.modelo.Evento;
import java.util.List;

/**
 * Interfaz que define los métodos para gestionar la entidad {@link Evento}.
 *
 * @author Nicolás Muñoz Miguel
 */
public interface ServicioEvento {
    /**
     * Retorna la lista de todos los eventos.
     *
     * @return Lista de {@link Evento}.
     */
    List<Evento> getAllEventos();

    /**
     * Busca y retorna un evento a partir de su identificador.
     *
     * @param id Identificador del evento.
     * @return El evento encontrado.
     */
    Evento getEventoById(Long id);

    /**
     * Guarda o actualiza un evento.
     *
     * @param evento Objeto {@link Evento} a guardar o actualizar.
     */
    void saveEvento(Evento evento);

    /**
     * Elimina un evento a partir de su identificador.
     *
     * @param id Identificador del evento a eliminar.
     */
    void deleteEvento(Long id);
}


