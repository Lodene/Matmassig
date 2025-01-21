package com.school.matmassig.recipeservice.model;

public class NotificationMessage {
    private String email;
    private String message;

    // tostring
    @Override
    public String toString() {
        return "NotificationMessage{" +
                "userId=" + email +
                ", message='" + message + '\'' +
                '}';
    }

    public NotificationMessage() {
    }

    public NotificationMessage(String email, String message) {
        this.email = email;
        this.message = message;
    }

    // Getters and Setters
    public String getUserId() {
        return email;
    }

    public void setUserId(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
