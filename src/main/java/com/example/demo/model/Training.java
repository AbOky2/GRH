package com.example.demo.model;

public class Training {
    private int id;
    private String title;
    private String description;
    private int duration; // en jours

    public Training(int id, String title, String description, int duration) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
    }

    // Getters et setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
