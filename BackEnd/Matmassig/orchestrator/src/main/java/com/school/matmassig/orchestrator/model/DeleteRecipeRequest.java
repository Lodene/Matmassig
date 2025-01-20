package com.school.matmassig.orchestrator.model;

public class DeleteRecipeRequest {

    private Integer id; // Recipe ID
    private Integer userId;

    public DeleteRecipeRequest() {}

    public DeleteRecipeRequest(Integer id, Integer userId) {
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
        return "DeleteRecipeRequest{" +
                "id=" + id +
                ", userId=" + userId +
                '}';
    }
}
