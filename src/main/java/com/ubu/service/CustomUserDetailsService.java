
package com.ubu.service;

import com.ubu.modelo.Usuario;
import com.ubu.repository.RepositorioUsuario;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
// com.ubu.modelo.UserRole is implicitly used via user.getRol().name()

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final RepositorioUsuario repositorioUsuario;

    public CustomUserDetailsService(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // RepositorioUsuario.findByUsername now returns Optional<Usuario>
        Usuario user = (Usuario) repositorioUsuario.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre: " + username));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRol().name().startsWith("ROLE_") ? user.getRol().name().substring("ROLE_".length()) : user.getRol().name()) // Corrected role handling
                .build();
    }
}

