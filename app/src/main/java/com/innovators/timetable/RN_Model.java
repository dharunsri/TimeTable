package com.innovators.timetable;

public class RN_Model {

    String title;
    String description;
    String date;
    String id;

    public RN_Model(String title, String description, String date, String id) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
