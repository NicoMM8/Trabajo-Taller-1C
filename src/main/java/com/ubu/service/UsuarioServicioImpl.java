package com.ubu.service;

import java.util.List;

import com.ubu.exception.UsuarioNotFoundException;
import org.springframework.stereotype.Service;
import com.ubu.modelo.Usuario;
import com.ubu.repository.RepositorioUsuario;

@Service
public class UsuarioServicioImpl implements ServicioUsuario {

    // Variable de instancia siguiendo la convención camelCase.
    private final RepositorioUsuario repositorioUsuario;

    public UsuarioServicioImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public List<Usuario> getAllUsers() {
        return repositorioUsuario.findAll();
    }

    @Override
    public Usuario getUserById(Long id) {
        // Se puede lanzar una excepción personalizada en caso de no encontrar un usuario
        return repositorioUsuario.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario con id " + id + " no fue encontrado"));
    }

    @Override
    public void saveUser(Usuario usuario) {
        repositorioUsuario.save(usuario);
    }

    @Override
    public void deleteUser(Long id) {
        // Se puede verificar si el usuario existe antes de eliminar
        if (!repositorioUsuario.existsById(id)){
            throw new UsuarioNotFoundException("Usuario con id " + id + " no existe");
        }
        repositorioUsuario.deleteById(id);
    }
}


