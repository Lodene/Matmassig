package com.school.matmassig.orchestrator.model;

public class PaginationRequest {

    private int page;
    private int size;

    public PaginationRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    // Getters et Setters
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
