package com.ubu.service;

import java.util.List;
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
        return eventoRepositorio.findById(id).orElse(null);
    }

    @Override
    public void saveEvento(Evento evento) {
        eventoRepositorio.save(evento);
    }

    @Override
    public void deleteEvento(Long id) {
        eventoRepositorio.deleteById(id);
    }
}

