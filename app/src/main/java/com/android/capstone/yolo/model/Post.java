package com.android.capstone.yolo.model;

import java.util.ArrayList;
import java.util.Date;

public class Post {
    public long id;
    public String title, content, type, writer;
    public ArrayList<String> image;
    public Date date;

    public void setId(long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public String getWriter() {
        return writer;
    }
}

