package com.innovators.timetable;

public class ac_Model {
    String EventType;
    String CollegeName;
    String EventDate;
    String id;
    String eventName;

    public ac_Model(String id, String eventType, String collegeName,String eventName, String eventDate) {
        this.EventType = eventType;
        this.CollegeName = collegeName;
        this.EventDate = eventDate;
        this.eventName = eventName;
        this.id = id;
    }

    public String getEventType() {
        return EventType;
    }

    public void setEventType(String eventType) {
        EventType = eventType;
    }

    public String getCollegeName() {
        return CollegeName;
    }

    public void setCollegeName(String collegeName) {
        CollegeName = collegeName;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

}
