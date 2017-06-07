package com.android.capstone.yolo.model;

public class BoardList {
    public String _id, title, user, tag, date, type;

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getUser() {
        return user;
    }

    public String getTag() {
        return tag;
    }

    public String getDate() {
        return date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
