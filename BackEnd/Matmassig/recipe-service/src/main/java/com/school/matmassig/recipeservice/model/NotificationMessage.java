package com.school.matmassig.recipeservice.model;

public class NotificationMessage {
    private int userId;
    private String message;
    private int status;

    // tostring
    @Override
    public String toString() {
        return "NotificationMessage{" +
                "userId=" + userId +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }

    public NotificationMessage() {
    }

    public NotificationMessage(int userId, String message, int status) {
        this.userId = userId;
        this.message = message;
        this.status = status;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
