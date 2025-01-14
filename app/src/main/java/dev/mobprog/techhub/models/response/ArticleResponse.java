package dev.mobprog.techhub.models.response;

import java.util.List;

import dev.mobprog.techhub.models.Article;

public class ArticleResponse {
    private String status;
    private int totalResults;
    private List<dev.mobprog.techhub.models.Article> articles;

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

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
