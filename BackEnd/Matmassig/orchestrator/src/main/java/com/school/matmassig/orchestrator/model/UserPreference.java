package com.school.matmassig.orchestrator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserPreference {

    @JsonProperty("email")
    private String email;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("userId")
    private int userId;
    @JsonProperty("genre")
    private int genre;
    @JsonProperty("value")
    private String value;

    // Getters et Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "UserPreference{" +
                "email='" + email + '\'' +
                ", id=" + id +
                ", userId=" + userId +
                ", genre=" + genre +
                ", value='" + value + '\'' +
                '}';
    }
}
