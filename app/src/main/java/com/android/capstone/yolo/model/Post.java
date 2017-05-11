package com.android.capstone.yolo.model;

public class Post {
    public String _id, title, content, tag, user, date, img[];

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImg(String[] img) {
        this.img = img;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public String get_id() {
        return _id;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public String getTag() {
        return tag;
    }

    public String getUser() {
        return user;
    }

    public String[] getImg() {
        return img;
    }
}

