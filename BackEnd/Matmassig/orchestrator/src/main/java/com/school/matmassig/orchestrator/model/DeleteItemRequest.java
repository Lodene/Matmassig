package com.school.matmassig.orchestrator.model;

public class DeleteItemRequest {

    private Integer id;
    private Integer userId;

    public DeleteItemRequest() {}

    public DeleteItemRequest(Integer id, Integer userId) {
        this.id = id;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "DeleteItemRequest{" +
                "id=" + id +
                ", userId=" + userId +
                '}';
    }
}
