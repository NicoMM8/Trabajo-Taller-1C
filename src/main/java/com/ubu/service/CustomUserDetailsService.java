package com.ubu.service;

import com.ubu.modelo.Usuario;
import com.ubu.repository.RepositorioUsuario;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Servicio personalizado que implementa {@link UserDetailsService} para la carga
 * de datos de usuarios en el proceso de autenticación.
 * <p>
 * Este servicio utiliza {@link RepositorioUsuario} para buscar el usuario 
 * en la base de datos a partir de su username. Si el usuario no se encuentra,
 * se lanza una {@link UsernameNotFoundException}.
 * </p>
 * 
 * @author Nicolás Muñoz Miguel
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final RepositorioUsuario repositorioUsuario;

    /**
     * Constructor para inyectar {@link RepositorioUsuario}.
     *
     * @param repositorioUsuario Repositorio que administra la entidad {@link Usuario}.
     */
    public CustomUserDetailsService(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    /**
     * Carga los detalles de un usuario a partir de su nombre de usuario.
     *
     * @param username El nombre de usuario a buscar.
     * @return Un objeto {@link UserDetails} que contiene la información necesaria para la autenticación.
     * @throws UsernameNotFoundException si el usuario no se encuentra.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = repositorioUsuario.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre: " + username));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRol().name()) // Convierte a mayúsculas
                .build();
    }

}


