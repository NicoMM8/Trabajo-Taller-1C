package com.ubu.modelo;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import javax.persistence.ManyToOne; // Added import
import javax.persistence.FetchType; // Added import
import javax.persistence.JoinColumn; // Added import

@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200, message = "El nombre debe tener máximo 200 caracteres")
    @Column(nullable = false)
    private String nombre;

    @Size(max = 1000, message = "La descripción puede tener hasta 1000 caracteres")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    @Column(nullable = false)
    private Double precio;

    @NotNull(message = "La fecha es obligatoria")
    @Column(nullable = false)
    private LocalDateTime fecha;

    @NotBlank(message = "La ubicación es obligatoria")
    @Column(nullable = false)
    private String ubicacion;

    @Min(value = 1, message = "El aforo debe ser al menos 1")
    private Integer aforo;

    @DecimalMin(value = "-90.0", inclusive = true, message = "La latitud mínima es -90.0")
    @DecimalMax(value = "90.0", inclusive = true, message = "La latitud máxima es 90.0")
    private Double latitud;

    @DecimalMin(value = "-180.0", inclusive = true, message = "La longitud mínima es -180.0")
    @DecimalMax(value = "180.0", inclusive = true, message = "La longitud máxima es 180.0")
    private Double longitud;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizador_id", nullable = false)
    private Usuario organizador;

    // Constructor vacío
    public Evento() {}

    // Constructor completo (incluye precio y organizador)
    public Evento(String nombre, String descripcion, LocalDateTime fecha,
                  String ubicacion, Integer aforo, Double precio,
                  Double latitud, Double longitud, Usuario organizador) { // Added Usuario organizador
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.aforo = aforo;
        this.precio = precio;
        this.latitud = latitud;
        this.longitud = longitud;
        this.organizador = organizador; // Added this line
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public Integer getAforo() { return aforo; }
    public void setAforo(Integer aforo) { this.aforo = aforo; }

    public Double getLatitud() { return latitud; }
    public void setLatitud(Double latitud) { this.latitud = latitud; }

    public Double getLongitud() { return longitud; }
    public void setLongitud(Double longitud) { this.longitud = longitud; }

    public Usuario getOrganizador() { return organizador; } // Added getter
    public void setOrganizador(Usuario organizador) { this.organizador = organizador; } // Added setter

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fecha=" + fecha +
                ", ubicacion='" + ubicacion + '\'' +
                ", aforo=" + aforo +
                ", precio=" + precio +
                ", organizador=" + (organizador != null ? organizador.getUsername() : "null") + // Updated toString
                '}';
    }
}





