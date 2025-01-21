package com.school.matmassig.orchestrator.model;

import org.springframework.web.bind.annotation.RequestHeader;

public class PaginationRequest {

    private String email;

    private int page;
    private int size;

    public PaginationRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    // Getters et Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "PaginationRequest{" +
                "page=" + page +
                ", size=" + size +
                '}';
    }
}
