package com.ubu.service;

import com.ubu.exception.EventoNotFoundException;
import com.ubu.modelo.Evento;
import com.ubu.repository.EventoRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio para gestionar operaciones relacionadas con la entidad {@link Evento}.
 * <p>
 * Este servicio ofrece métodos para obtener, guardar y eliminar eventos utilizando el repositorio
 * {@link EventoRepositorio}. En caso de que no se encuentre un evento, se lanza una excepción personalizada
 * {@link EventoNotFoundException}.
 * </p>
 * 
 * @author Nicolás Muñoz Miguel
 */
@Service
public class EventoServiceImpl implements ServicioEvento {

    private final EventoRepositorio eventoRepositorio;

    /**
     * Constructor con inyección de dependencias para inicializar el repositorio de eventos.
     *
     * @param eventoRepositorio Repositorio que administra la entidad {@link Evento}.
     */
    public EventoServiceImpl(EventoRepositorio eventoRepositorio) {
        this.eventoRepositorio = eventoRepositorio;
    }

    /**
     * Obtiene todos los eventos almacenados.
     *
     * @return Una lista de todos los eventos.
     */
    @Override
    public List<Evento> getAllEventos() {
        return eventoRepositorio.findAll();
    }

    /**
     * Obtiene un evento por su identificador.
     * <p>
     * Si no se encuentra un evento con el ID proporcionado, se lanza una excepción
     * {@link EventoNotFoundException}.
     * </p>
     *
     * @param id Identificador único del evento.
     * @return El evento encontrado.
     * @throws EventoNotFoundException Si no existe un evento con el ID dado.
     */
    @Override
    public Evento getEventoById(Long id) {
        return eventoRepositorio.findById(id)
                .orElseThrow(() -> new EventoNotFoundException("El evento con id " + id + " no fue encontrado."));
    }

    /**
     * Guarda o actualiza un evento en la base de datos.
     *
     * @param evento El objeto {@link Evento} a guardar o actualizar.
     */
    @Override
    public void saveEvento(Evento evento) {
        eventoRepositorio.save(evento);
    }

    /**
     * Elimina un evento por su identificador.
     * <p>
     * Antes de la eliminación, se verifica si el evento existe. En caso contrario,
     * se lanza una excepción {@link EventoNotFoundException}.
     * </p>
     *
     * @param id Identificador del evento a eliminar.
     * @throws EventoNotFoundException Si no existe un evento con el ID proporcionado.
     */
    @Override
    public void deleteEvento(Long id) {
        if (!eventoRepositorio.existsById(id)) {
            throw new EventoNotFoundException("El evento con id " + id + " no existe");
        }
        eventoRepositorio.deleteById(id);
    }
}


