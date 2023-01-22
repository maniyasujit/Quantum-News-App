package com.example.quantumnewsapp.Model;

import java.util.ArrayList;

public class ParentNewsModel {
    private String status;
    private int totalResults;
    private ArrayList<NewsModel> articles;

    public ParentNewsModel() {
    }

    public ParentNewsModel(String status, int totalResults, ArrayList<NewsModel> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<NewsModel> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<NewsModel> articles) {
        this.articles = articles;
    }
}
