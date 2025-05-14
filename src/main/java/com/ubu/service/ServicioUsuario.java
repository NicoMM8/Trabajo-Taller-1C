package com.ubu.service;

import java.util.List;
import com.ubu.modelo.Usuario;

public interface ServicioUsuario {
    List<Usuario> getAllUsers();
    Usuario getUserById(Long id);
    Usuario saveUser(Usuario user);
    void deleteUser(Long id);
}

