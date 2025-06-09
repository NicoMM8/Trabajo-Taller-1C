package com.ubu.service;

import com.ubu.modelo.Usuario;
import java.util.List;

/**
 * Interfaz que define las operaciones básicas para gestionar la entidad {@link Usuario}.
 * 
 * @author Nicolás Muñoz Miguel
 */
public interface ServicioUsuario {
    /**
     * Retorna la lista de todos los usuarios.
     *
     * @return Lista de {@link Usuario}.
     */
    List<Usuario> getAllUsers();

    /**
     * Obtiene un usuario a partir de su identificador.
     *
     * @param id Identificador del usuario.
     * @return El usuario encontrado.
     */
    Usuario getUserById(Long id);

    /**
     * Guarda o actualiza un usuario.
     *
     * @param user Objeto {@link Usuario} a guardar o actualizar.
     */
    void saveUser(Usuario user);

    /**
     * Elimina un usuario a partir de su identificador.
     *
     * @param id Identificador del usuario a eliminar.
     */
    void deleteUser(Long id);

    /**
     * Obtiene un usuario a partir de su nombre de usuario.
     *
     * @param username Nombre de usuario.
     * @return El usuario encontrado.
     */
    Usuario getUserByUsername(String username);
}


