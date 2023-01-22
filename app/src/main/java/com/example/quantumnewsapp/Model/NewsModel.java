package com.example.quantumnewsapp.Model;

public class NewsModel {
    private String title, description, url, urlToImage, publishedAt;
    private NewsSourceModel source;

    public NewsModel() {
    }

    public NewsModel(String title, String description, String url, String urlToImage, String publishedAt, NewsSourceModel source) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public NewsSourceModel getSource() {
        return source;
    }

    public void setSource(NewsSourceModel source) {
        this.source = source;
    }
}
