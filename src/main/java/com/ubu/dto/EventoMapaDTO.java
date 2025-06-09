package com.ubu.dto;

/**
 * DTO para representar la información mínima de un evento en el mapa.
 * <p>
 * Incluye el identificador, nombre y coordenadas geográficas del evento.
 * Se utiliza para enviar solo los datos necesarios a la vista del mapa.
 * </p>
 * 
 * @author Nicolás Muñoz Miguel
 */
public class EventoMapaDTO {
    private Long id;
    private String nombre;
    private Double latitud;
    private Double longitud;

    /**
     * Constructor para inicializar el DTO con los datos mínimos del evento.
     *
     * @param id       Identificador del evento.
     * @param nombre   Nombre del evento.
     * @param latitud  Latitud geográfica.
     * @param longitud Longitud geográfica.
     */
    public EventoMapaDTO(Long id, String nombre, Double latitud, Double longitud) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // Getters y setters

    /**
     * Obtiene el identificador del evento.
     * @return ID del evento.
     */
    public Long getId() { return id; }

    /**
     * Obtiene el nombre del evento.
     * @return Nombre del evento.
     */
    public String getNombre() { return nombre; }

    /**
     * Obtiene la latitud del evento.
     * @return Latitud.
     */
    public Double getLatitud() { return latitud; }

    /**
     * Obtiene la longitud del evento.
     * @return Longitud.
     */
    public Double getLongitud() { return longitud; }
}
