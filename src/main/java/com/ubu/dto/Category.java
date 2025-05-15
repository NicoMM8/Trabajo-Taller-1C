package com.ubu.dto;

public class Category {
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

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

