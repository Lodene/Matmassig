package com.school.matmassig.orchestrator.model;

public class DeleteRecipeRequest {

    private Integer id;

    public DeleteRecipeRequest() {}

    public DeleteRecipeRequest(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DeleteRecipeRequest{" +
                "id=" + id +
                '}';
    }
}
