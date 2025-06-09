package com.ubu.dto;

import lombok.Getter;

/**
 * DTO para representar una categoría de eventos o actividades.
 * <p>
 * Contiene información básica como el identificador, nombre, descripción y el ícono asociado
 * a la categoría, que puede ser un fragmento HTML o una clase CSS.
 * </p>
 * 
 * Se utiliza en la capa de presentación para enviar datos a las vistas.
 * 
 * @author Nicolás Muñoz Miguel
 */
@Getter
public class Category {

    /**
     * Identificador único de la categoría.
     */
    private Integer id;
    
    /**
     * Nombre de la categoría.
     */
    private String name;
    
    /**
     * Descripción breve de la categoría.
     */
    private String description;
    
    /**
     * Ícono asociado, representado como HTML o una clase CSS.
     */
    private String icon;

    /**
     * Constructor vacío, necesario para frameworks o en procesos de serialización.
     */
    public Category() {}


    /**
     * Constructor con parámetros para inicializar todas las propiedades de la categoría.
     *
     * @param id          Identificador único de la categoría.
     * @param name        Nombre de la categoría.
     * @param description Descripción de la categoría.
     * @param icon        Ícono asociado a la categoría.
     */
    public Category(Integer id, String name, String description, String icon) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

    /**
     * Establece el identificador único de la categoría.
     *
     * @param id Identificador único a asignar.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Establece el nombre de la categoría.
     *
     * @param name Nombre a asignar a la categoría.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Establece la descripción de la categoría.
     *
     * @param description Descripción a asignar a la categoría.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Establece el ícono asociado a la categoría.
     *
     * @param icon HTML o clase CSS que representa el ícono.
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

}


