package com.school.matmassig.orchestrator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ingredient {

    @JsonProperty("ingredientId")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("quantity")
    private String quantity;

    @JsonProperty("unit")
    private String unit;

    // Getters et Setters
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() { return unit;}

    public void setUnit(String unit) { this.unit = unit; }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
