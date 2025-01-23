package com.school.matmassig.notificationservice.models;

public class MessageEntity {
    private String message;
    private String email;


    public MessageEntity(String message, String email) {
        this.message = message;
        this.email = email;
    }

    public MessageEntity() {

    }

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
