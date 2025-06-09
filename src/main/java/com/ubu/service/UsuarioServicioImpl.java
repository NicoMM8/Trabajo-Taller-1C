package com.ubu.service;

import com.ubu.exception.UsuarioNotFoundException;
import com.ubu.modelo.Usuario;
import com.ubu.repository.RepositorioUsuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Implementación del servicio para gestionar la entidad {@link Usuario}.
 * <p>
 * Este servicio utiliza {@link RepositorioUsuario} para acceder a la base de datos
 * y recupera o actualiza los datos de los usuarios. Se hace uso de un {@link PasswordEncoder}
 * para encriptar la contraseña antes de guardar el usuario.
 * </p>
 * 
 * Se marca la clase con @Transactional para asegurar que las operaciones se ejecuten
 * en un contexto transaccional.
 * 
 * @author Nicolás Muñoz Miguel
 */
@Service
@Transactional 
public class UsuarioServicioImpl implements ServicioUsuario {

    private final RepositorioUsuario repositorioUsuario;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param repositorioUsuario Repositorio para gestionar la entidad {@link Usuario}.
     * @param passwordEncoder    Codificador de contraseñas para encriptar la contraseña del usuario.
     */
    public UsuarioServicioImpl(RepositorioUsuario repositorioUsuario, PasswordEncoder passwordEncoder) {
        this.repositorioUsuario = repositorioUsuario;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Retorna la lista de todos los usuarios.
     *
     * @return Lista de {@link Usuario}.
     */
    @Override
    @Transactional(readOnly = true) 
    public List<Usuario> getAllUsers() {
        return repositorioUsuario.findAll();
    }

    /**
     * Obtiene un usuario a partir de su identificador.
     * <p>
     * Si no se encuentra el usuario, se lanza {@link UsuarioNotFoundException}.
     * </p>
     *
     * @param id Identificador del usuario.
     * @return El usuario encontrado.
     * @throws UsuarioNotFoundException Si el usuario no existe.
     */
    @Override
    @Transactional(readOnly = true) 
    public Usuario getUserById(Long id) {
        return repositorioUsuario.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario con id " + id + " no fue encontrado"));
    }

    /**
     * Guarda o actualiza un usuario.
     * <p>
     * Antes de guardar, se encripta la contraseña utilizando {@link PasswordEncoder}.
     * </p>
     *
     * @param usuario Objeto {@link Usuario} a guardar.
     */
    @Override
    public void saveUser(Usuario usuario) {
        // Solo codifica si la contraseña no está ya codificada
        if (!usuario.getPassword().startsWith("$2a$")) { // BCrypt hash empieza así
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        repositorioUsuario.save(usuario);
    }

    /**
     * Elimina un usuario a partir de su identificador.
     * <p>
     * Si el usuario no existe, se lanza {@link UsuarioNotFoundException}.
     * </p>
     *
     * @param id Identificador del usuario a eliminar.
     * @throws UsuarioNotFoundException Si no existe un usuario con el id dado.
     */
    @Override
    public void deleteUser(Long id) {
        if (!repositorioUsuario.existsById(id)){
            throw new UsuarioNotFoundException("Usuario con id " + id + " no existe");
        }
        repositorioUsuario.deleteById(id);
    }

    /**
     * Obtiene un usuario a partir de su nombre de usuario.
     * <p>
     * Si no se encuentra el usuario, se lanza {@link UsuarioNotFoundException}.
     * </p>
     *
     * @param username Nombre de usuario.
     * @return El usuario encontrado.
     * @throws UsuarioNotFoundException Si el usuario no existe.
     */
    @Override
    @Transactional(readOnly = true)
    public Usuario getUserByUsername(String username) {
        return repositorioUsuario.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario con username " + username + " no fue encontrado"));
    }
}



