package com.ubu.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.ubu.modelo.Usuario;
import com.ubu.repository.RepositorioUsuario;

@Service
public class UsuarioServicioImpl implements ServicioUsuario {

    private final RepositorioUsuario RepositorioUsuario;

    public UsuarioServicioImpl(RepositorioUsuario repositorioUsuario) {
        this.RepositorioUsuario = repositorioUsuario;
    }

    @Override
    public List<Usuario> getAllUsers() {
        return RepositorioUsuario.findAll();
    }

    @Override
    public Usuario getUserById(Long id) {
        return RepositorioUsuario.findById(id).orElse(null);
    }

    @Override
    public void saveUser(Usuario usuario) {
        RepositorioUsuario.save(usuario);
    }

    @Override
    public void deleteUser(Long id) {
        RepositorioUsuario.deleteById(id);
    }
}

