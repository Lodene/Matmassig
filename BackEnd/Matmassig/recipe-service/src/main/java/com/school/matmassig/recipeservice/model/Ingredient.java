package com.school.matmassig.recipeservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    // constructor
    public Ingredient() {
        // Default constructor required by JPA
    }

    public Ingredient(String name) {
        this.name = name;
    }

    // Getters and Setters

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
}