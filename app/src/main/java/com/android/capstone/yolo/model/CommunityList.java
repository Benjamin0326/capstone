package com.android.capstone.yolo.model;

public class CommunityList {
    public long id;
    public String title;
    public String sub;
    public String path;

    public void setPath(String path) {
        this.path = path;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public String getSub() {
        return sub;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
