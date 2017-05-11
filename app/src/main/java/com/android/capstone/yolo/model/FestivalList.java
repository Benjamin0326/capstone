package com.android.capstone.yolo.model;

public class FestivalList {

    // mainImage : imageList[0]
    // infoImage : imageList[1]
    // infoImageList : imageList[2] ~
    String _id, title, content, startDate, endDate, location;
    String[] img, vedio;

    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }
    public String[] getImg(){
        return img;
    }
    public String getStartDate(){
        return startDate;
    }
    public String getEndDate(){
        return endDate;
    }
    public String getLocation(){
        return location;
    }
    public String[] getVedio(){
        return vedio;
    }
    public String get_id(){
        return _id;
    }
    void setTitle(String _title){
        this.title = _title;
    }
    void setContent(String _content){
        this.content = _content;
    }
    void setImg(String[] _img){
        this.img = _img;
    }
    void setStartDate(String _startDate){
        this.startDate = _startDate;
    }
    void setEndDate(String _endDate){
        this.endDate = _endDate;
    }
    void setLocation(String _location){
        this.location = _location;
    }
    void setVedio(String[] _vedio){
        this.vedio = _vedio;
    }
    void set_id(String __id){
        this._id = __id;
    }
}
