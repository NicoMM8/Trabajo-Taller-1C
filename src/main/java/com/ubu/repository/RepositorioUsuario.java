package com.ubu.repository;

import com.ubu.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repositorio para la entidad {@link Usuario}.
 * <p>
 * Permite gestionar las operaciones CRUD y define consultas personalizadas, como
 * obtener un usuario a partir de su nombre de usuario.
 * </p>
 * 
 * @author Nicolás Muñoz Miguel
 */
public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su username.
     *
     * @param username Nombre de usuario a buscar.
     * @return Un {@link Optional} que contiene el usuario si se encuentra, o vacío en caso contrario.
     */
    Optional<Usuario> findByUsername(String username);
}


