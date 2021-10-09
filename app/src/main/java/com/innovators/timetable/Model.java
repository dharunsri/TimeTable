package com.innovators.timetable;

public class Model {

    String id;
    String title;
    String link;
    String day;
    String stime;
    String etime;


    public Model(String id, String title, String link, String day,String stime, String etime) {
        this.title = title;
        this.stime = stime;
        this.link = link;
        this.day = day;
        this.etime = etime;
        this.id = id;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
