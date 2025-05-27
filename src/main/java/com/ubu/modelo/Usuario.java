package com.ubu.modelo;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.persistence.Enumerated; // Added import
import javax.persistence.EnumType; // Added import
import java.util.Set; // Added import
import java.util.HashSet; // Added import
import javax.persistence.OneToMany; // Added import
import javax.persistence.CascadeType; // Added import
import javax.persistence.FetchType; // Added import
// com.ubu.modelo.Evento is implicitly used by Set<Evento> and mappedBy

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El usuario es obligatorio")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING) // Changed annotation
    @Column(nullable = false) // Kept nullable = false from previous @NotBlank
    private UserRole rol; // Changed type to UserRole

    // Este campo es usado solo en la validación del registro, no se persiste en la BD.
    @Transient
    private String confirmPassword;

    @OneToMany(mappedBy = "organizador", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Evento> eventosCreados = new HashSet<>();

    // Constructores
    public Usuario() {}

    // Updated constructor to accept UserRole
    public Usuario(String username, String password, String email, UserRole rol) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.rol = rol;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Updated getter for rol
    public UserRole getRol() { return rol; }
    // Updated setter for rol
    public void setRol(UserRole rol) { this.rol = rol; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    // Getters and setters for eventosCreados
    public Set<Evento> getEventosCreados() { return eventosCreados; }
    public void setEventosCreados(Set<Evento> eventosCreados) { this.eventosCreados = eventosCreados; }
}



