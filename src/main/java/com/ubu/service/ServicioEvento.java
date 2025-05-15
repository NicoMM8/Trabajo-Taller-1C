package com.ubu.service;

import java.util.List;
import com.ubu.modelo.Evento;

public interface ServicioEvento {
    List<Evento> getAllEventos();
    Evento getEventoById(Long id);
    void saveEvento(Evento evento);
    void deleteEvento(Long id);
}

