package com.example.ursers.model.dto;

import java.util.List;

public class Page<T> {
    public List<T> content;
    public int totalPage;
    public int currentPage;

    public Page() {
    }

    public Page(List<T> content, int totalPage, int currentPage) {
        this.content = content;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
