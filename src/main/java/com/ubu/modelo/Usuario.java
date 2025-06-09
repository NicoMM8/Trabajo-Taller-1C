package com.ubu.modelo;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Representa un usuario de la aplicación.
 * <p>
 * Esta entidad se mapea a la tabla usuarios de la base de datos e incluye
 * información requerida para el registro, autenticación y gestión del usuario. Además, define la relación
 * con los eventos que el usuario crea (cuando actúa como organizador).
 * </p>
 * 
 * El campo confirmPassword se utiliza únicamente en el proceso de validación del registro y no se persiste.
 * 
 * @author Nicolás Muñoz Miguel
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    /**
     * Identificador único del usuario, se genera automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de usuario, que debe ser único y no nulo.
     */
    @NotBlank(message = "El usuario es obligatorio")
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * Contraseña del usuario.
     * Se recomienda almacenar esta contraseña en forma encriptada.
     */
    @NotBlank(message = "La contraseña es obligatoria")
    @Column(nullable = false)
    private String password;

    /**
     * Email único del usuario.
     * Se valida que contenga un formato de correo electrónico.
     */
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Rol asignado al usuario.
     * Se mapea el enum {@link UserRole} a una cadena en la BD.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole rol;

    /**
     * Campo utilizado únicamente para la confirmación de contraseña durante el registro.
     * Este campo no se persiste en la base de datos.
     */
    @Transient
    private String confirmPassword;

    /**
     * Conjunto de eventos creados por el usuario, útil cuando el usuario actúa como organizador.
     * La relación está gestionada de forma bidireccional con la entidad {@link Evento}.
     * Se usa cascade para propagar operaciones y orphanRemoval para eliminar eventos huérfanos.
     */
    @OneToMany(mappedBy = "organizador", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Evento> eventosCreados = new HashSet<>();

    /**
     * Constructor vacío, requerido por JPA.
     */
    public Usuario() {}

    /**
     * Constructor que inicializa un usuario con los datos básicos.
     * 
     * @param username Nombre de usuario.
     * @param password Contraseña del usuario.
     * @param email    Email del usuario.
     * @param rol      Rol asignado al usuario.
     */
    public Usuario(String username, String password, String email, UserRole rol) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.rol = rol;
    }

    // Getters y Setters

    /**
     * Obtiene el identificador único del usuario.
     * 
     * @return ID del usuario.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del usuario.
     * 
     * @param id El ID a asignar.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de usuario.
     * 
     * @return Nombre de usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario.
     * 
     * @param username Nombre de usuario a asignar.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene la contraseña del usuario.
     * 
     * @return Contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     * 
     * @param password Contraseña a asignar.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el email del usuario.
     * 
     * @return Email del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del usuario.
     * 
     * @param email Email a asignar.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el rol asignado al usuario.
     * 
     * @return Rol del usuario.
     */
    public UserRole getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     * 
     * @param rol Rol a asignar.
     */
    public void setRol(UserRole rol) {
        this.rol = rol;
    }

    /**
     * Obtiene el valor de confirmPassword (para validación de registro).
     * 
     * @return Valor de confirmación de contraseña.
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Establece el valor de confirmPassword.
     * 
     * @param confirmPassword Valor a asignar para la confirmación de la contraseña.
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * Obtiene el conjunto de eventos que el usuario ha creado.
     * 
     * @return Conjunto de eventos creados.
     */
    public Set<Evento> getEventosCreados() {
        return eventosCreados;
    }

    /**
     * Establece el conjunto de eventos que el usuario ha creado.
     * 
     * @param eventosCreados Conjunto de eventos a asignar.
     */
    public void setEventosCreados(Set<Evento> eventosCreados) {
        this.eventosCreados = eventosCreados;
    }
}




