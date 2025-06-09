package com.ubu.modelo;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * Representa un evento en la aplicación.
 * <p>
 * Entidad que mapea a la tabla <strong>eventos</strong> de la base de datos y contiene
 * información relevante para describir un evento, incluyendo validaciones para asegurar
 * la integridad de los datos.
 * </p>
 * 
 * Incluye datos como nombre, descripción, precio, fecha, ubicación, capacidad (aforo),
 * coordenadas geográficas y el usuario organizador.
 * 
 * @author Nicolás Muñoz Miguel
 */
@Entity
@Table(name = "eventos")
public class Evento {

    /**
     * Identificador único del evento. Se genera automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del evento.
     * Debe ser no vacío y tener un máximo de 200 caracteres.
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200, message = "El nombre debe tener máximo 200 caracteres")
    @Column(nullable = false)
    private String nombre;

    /**
     * Descripción del evento.
     * Se permite hasta 1000 caracteres.
     */
    @Size(max = 1000, message = "La descripción puede tener hasta 1000 caracteres")
    private String descripcion;

    /**
     * Precio de entrada al evento.
     * Debe ser mayor que 0 y no nulo.
     */
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    @Column(nullable = false)
    private Double precio;

    /**
     * Fecha y hora en que se realizará el evento.
     * Es obligatoria y no puede ser en pasado.
     */
    @Future(message = "La fecha debe ser futura")
    @NotNull(message = "La fecha es obligatoria")
    @Column(nullable = false)
    private LocalDateTime fecha;

    /**
     * Ubicación o dirección donde se celebrará el evento.
     * Es obligatoria.
     */
    @NotBlank(message = "La ubicación es obligatoria")
    @Column(nullable = false)
    private String ubicacion;

    /**
     * Aforo o capacidad mínima del evento.
     * Debe ser al menos 1.
     */
    @Min(value = 1, message = "El aforo debe ser al menos 1")
    private Integer aforo;

    /**
     * Latitud geográfica del evento.
     * Debe estar entre -90.0 y 90.0.
     */
    @DecimalMin(value = "-90.0", inclusive = true, message = "La latitud mínima es -90.0")
    @DecimalMax(value = "90.0", inclusive = true, message = "La latitud máxima es 90.0")
    private Double latitud;

    /**
     * Longitud geográfica del evento.
     * Debe estar entre -180.0 y 180.0.
     */
    @DecimalMin(value = "-180.0", inclusive = true, message = "La longitud mínima es -180.0")
    @DecimalMax(value = "180.0", inclusive = true, message = "La longitud máxima es 180.0")
    private Double longitud;

    /**
     * Usuario organizador del evento.
     * Se establece una relación Many-to-One, ya que un organizador puede tener muchos eventos.
     * Se usa carga perezosa para evitar cargar el organizador innecesariamente.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizador_id", nullable = false)
    private Usuario organizador;

    /**
     * Constructor vacío, requerido por algunos frameworks y para procesos de serialización.
     */
    public Evento() {}

    /**
     * Constructor completo para inicializar un evento.
     * 
     * @param nombre       Nombre del evento.
     * @param descripcion  Descripción del evento.
     * @param fecha        Fecha y hora del evento.
     * @param ubicacion    Ubicación donde se realizará el evento.
     * @param aforo        Capacidad o aforo del evento.
     * @param precio       Precio de entrada para el evento.
     * @param latitud      Latitud geográfica del evento.
     * @param longitud     Longitud geográfica del evento.
     * @param organizador  Usuario que organiza el evento.
     */
    public Evento(String nombre, String descripcion, LocalDateTime fecha,
                  String ubicacion, Integer aforo, Double precio,
                  Double latitud, Double longitud, Usuario organizador) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.aforo = aforo;
        this.precio = precio;
        this.latitud = latitud;
        this.longitud = longitud;
        this.organizador = organizador;
    }

    // Getters y setters

    /**
     * Obtiene el identificador del evento.
     * 
     * @return ID del evento.
     */
    public Long getId() { 
        return id; 
    }

    /**
     * Establece el identificador del evento.
     * 
     * @param id ID a asignar.
     */
    public void setId(Long id) { 
        this.id = id; 
    }

    /**
     * Obtiene el nombre del evento.
     * 
     * @return Nombre del evento.
     */
    public String getNombre() { 
        return nombre; 
    }

    /**
     * Establece el nombre del evento.
     * 
     * @param nombre Nombre a asignar.
     */
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    /**
     * Obtiene la descripción del evento.
     * 
     * @return Descripción del evento.
     */
    public String getDescripcion() { 
        return descripcion; 
    }

    /**
     * Establece la descripción del evento.
     * 
     * @param descripcion Descripción a asignar.
     */
    public void setDescripcion(String descripcion) { 
        this.descripcion = descripcion; 
    }

    /**
     * Obtiene el precio del evento.
     * 
     * @return Precio del evento.
     */
    public Double getPrecio() { 
        return precio; 
    }

    /**
     * Establece el precio del evento.
     * 
     * @param precio Precio a asignar.
     */
    public void setPrecio(Double precio) { 
        this.precio = precio; 
    }

    /**
     * Obtiene la fecha y hora del evento.
     * 
     * @return Fecha y hora del evento.
     */
    public LocalDateTime getFecha() { 
        return fecha; 
    }

    /**
     * Establece la fecha y hora del evento.
     * 
     * @param fecha Fecha y hora a asignar.
     */
    public void setFecha(LocalDateTime fecha) { 
        this.fecha = fecha; 
    }

    /**
     * Obtiene la ubicación del evento.
     * 
     * @return Ubicación del evento.
     */
    public String getUbicacion() { 
        return ubicacion; 
    }

    /**
     * Establece la ubicación del evento.
     * 
     * @param ubicacion Ubicación a asignar.
     */
    public void setUbicacion(String ubicacion) { 
        this.ubicacion = ubicacion; 
    }

    /**
     * Obtiene el aforo del evento.
     * 
     * @return Capacidad o aforo del evento.
     */
    public Integer getAforo() { 
        return aforo; 
    }

    /**
     * Establece el aforo del evento.
     * 
     * @param aforo Aforo a asignar.
     */
    public void setAforo(Integer aforo) { 
        this.aforo = aforo; 
    }

    /**
     * Obtiene la latitud donde se realizará el evento.
     * 
     * @return Latitud del evento.
     */
    public Double getLatitud() { 
        return latitud; 
    }

    /**
     * Establece la latitud del evento.
     * 
     * @param latitud Latitud a asignar.
     */
    public void setLatitud(Double latitud) { 
        this.latitud = latitud; 
    }

    /**
     * Obtiene la longitud donde se realizará el evento.
     * 
     * @return Longitud del evento.
     */
    public Double getLongitud() { 
        return longitud; 
    }

    /**
     * Establece la longitud del evento.
     * 
     * @param longitud Longitud a asignar.
     */
    public void setLongitud(Double longitud) { 
        this.longitud = longitud; 
    }

    /**
     * Obtiene el usuario organizador del evento.
     * 
     * @return Usuario organizador.
     */
    public Usuario getOrganizador() { 
        return organizador; 
    }

    /**
     * Establece el usuario organizador del evento.
     * 
     * @param organizador Usuario organizador a asignar.
     */
    public void setOrganizador(Usuario organizador) { 
        this.organizador = organizador; 
    }

    /**
     * Devuelve una representación en texto del evento, incluyendo información relevante.
     * 
     * @return Cadena de texto representando el evento.
     */
    @Override
    public String toString() {
        return "Evento{" +
               "id=" + id +
               ", nombre='" + nombre + '\'' +
               ", fecha=" + fecha +
               ", ubicacion='" + ubicacion + '\'' +
               ", aforo=" + aforo +
               ", precio=" + precio +
               // Se accede a getUsername() solo si el organizador está inicializado
               ", organizador=" + (organizador != null ? organizador.getUsername() : "null") +
               '}';
    }
}






