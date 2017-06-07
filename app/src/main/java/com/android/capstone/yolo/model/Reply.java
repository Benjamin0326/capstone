package com.android.capstone.yolo.model;

public class Reply {
    public String _id, user, content, date, path, boardId;

    public void setBoardId(String _boardId){
        boardId = _boardId;
    }

    public String getBoardId(){
        return boardId;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPath(String path) {
        this.path = path;
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

    public String getUser() {
        return user;
    }

    public String getPath() {
        return path;
    }
}
