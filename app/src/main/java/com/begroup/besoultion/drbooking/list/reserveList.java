package com.begroup.besoultion.drbooking.list;

public class reserveList {
    String date,from,to;
    int id,userId;

    public reserveList(String date, String from, String to,int id,int userId) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.id=id;
        this.userId=userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

