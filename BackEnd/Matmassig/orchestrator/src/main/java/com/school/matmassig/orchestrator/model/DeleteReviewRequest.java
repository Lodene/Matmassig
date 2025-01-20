package com.school.matmassig.orchestrator.model;

public class DeleteReviewRequest {

    private Integer id; // Review ID
    private Integer userId;

    public DeleteReviewRequest() {}

    public DeleteReviewRequest(Integer id, Integer userId) {
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
        return "DeleteReviewRequest{" +
                "id=" + id +
                ", userId=" + userId +
                '}';
    }
}
