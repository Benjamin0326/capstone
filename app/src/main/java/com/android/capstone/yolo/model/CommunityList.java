package com.android.capstone.yolo.model;

public class CommunityList {
    public String _id, title, startDate, endDate, location, icon;

    public String getEndDate() {
        return endDate;
    }

    public String getIcon() {
        return icon;
    }

    public String getId() {
        return _id;
    }

    public String getLocation() {
        return location;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
