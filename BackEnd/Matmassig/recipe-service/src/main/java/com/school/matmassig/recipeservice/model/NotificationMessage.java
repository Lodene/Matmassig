package com.school.matmassig.recipeservice.model;

public class NotificationMessage {
    private String email;
    private String message;

    @Override
    public String toString() {
        return "NotificationMessage{" +
                "email='" + email + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public NotificationMessage() {
    }

    public NotificationMessage(String email, String message) {
        this.email = email;
        this.message = message;
    }

    // Utiliser "email" au lieu de "userId"
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
