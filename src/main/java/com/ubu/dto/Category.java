package com.ubu.dto;

import lombok.Getter;

@Getter
public class Category {
    // Getters y setters
    private Integer id;
    private String name;
    private String description;
    private String icon; // Aquí puedes almacenar el HTML o la clase CSS correspondiente al ícono

    // Constructor vacío (necesario para algunos frameworks o serializaciones)
    public Category() {}

    // Constructor con parámetros
    public Category(Integer id, String name, String description, String icon) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

