
package com.ubu.service;

import java.util.List;

import com.ubu.exception.UsuarioNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder; // Added import
import org.springframework.stereotype.Service;
import com.ubu.modelo.Usuario;
import com.ubu.repository.RepositorioUsuario;
import org.springframework.transaction.annotation.Transactional; // Added import

@Service
@Transactional 
public class UsuarioServicioImpl implements ServicioUsuario {

    private final RepositorioUsuario repositorioUsuario;
    private final PasswordEncoder passwordEncoder; // Added PasswordEncoder field

    // Constructor injection for RepositorioUsuario and PasswordEncoder
    public UsuarioServicioImpl(RepositorioUsuario repositorioUsuario, PasswordEncoder passwordEncoder) {
        this.repositorioUsuario = repositorioUsuario;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true) 
    public List<Usuario> getAllUsers() {
        return repositorioUsuario.findAll();
    }

    @Override
    @Transactional(readOnly = true) 
    public Usuario getUserById(Long id) {
        return repositorioUsuario.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario con id " + id + " no fue encontrado"));
    }

    @Override
    public void saveUser(Usuario usuario) {
        // Encode password before saving
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        repositorioUsuario.save(usuario);
    }

    @Override
    public void deleteUser(Long id) {
        if (!repositorioUsuario.existsById(id)){
            throw new UsuarioNotFoundException("Usuario con id " + id + " no existe");
        }
        repositorioUsuario.deleteById(id);
    }
}


