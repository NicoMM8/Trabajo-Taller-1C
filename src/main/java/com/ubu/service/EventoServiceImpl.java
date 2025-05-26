package com.ubu.service;

import java.util.List;

import com.ubu.exception.EventoNotFoundException;
import org.springframework.stereotype.Service;
import com.ubu.modelo.Evento;
import com.ubu.repository.EventoRepositorio;

@Service
public class EventoServiceImpl implements ServicioEvento {

    private final EventoRepositorio eventoRepositorio;

    public EventoServiceImpl(EventoRepositorio eventoRepositorio) {
        this.eventoRepositorio = eventoRepositorio;
    }

    @Override
    public List<Evento> getAllEventos() {
        return eventoRepositorio.findAll();
    }

    @Override
    public Evento getEventoById(Long id) {
        // Podrías lanzar una excepción si no se encuentra el evento
        return eventoRepositorio.findById(id)
                .orElseThrow(() -> new EventoNotFoundException("El evento con id " + id + " no fue encontrado."));
    }

    @Override
    public void saveEvento(Evento evento) {
        eventoRepositorio.save(evento);
    }

    @Override
    public void deleteEvento(Long id) {
        if (!eventoRepositorio.existsById(id)) {
            throw new EventoNotFoundException("El evento con id " + id + " no existe");
        }
        eventoRepositorio.deleteById(id);
    }
}


