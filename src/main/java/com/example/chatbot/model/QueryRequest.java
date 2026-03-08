package com.example.chatbot.model;

public class QueryRequest {
    private String query;
    private String model;

    // Constructors
    public QueryRequest() {}

    public QueryRequest(String query, String model) {
        this.query = query;
        this.model = model;
    }

    // Getters and Setters
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}