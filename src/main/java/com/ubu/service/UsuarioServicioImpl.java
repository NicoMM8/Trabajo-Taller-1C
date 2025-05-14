package com.ubu.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ubu.modelo.Usuario;
import com.ubu.repository.RepositorioUsuario;

@Service
public class UsuarioServicioImpl implements ServicioUsuario {

    @Autowired
    private RepositorioUsuario RepositorioUsuario;

    @Override
    public List<Usuario> getAllUsers() {
        return RepositorioUsuario.findAll();
    }

    @Override
    public Usuario getUserById(Long id) {
        return RepositorioUsuario.findById(id).orElse(null);
    }

    @Override
    public Usuario saveUser(Usuario usuario) {
        return RepositorioUsuario.save(usuario);
    }

    @Override
    public void deleteUser(Long id) {
        RepositorioUsuario.deleteById(id);
    }
}

