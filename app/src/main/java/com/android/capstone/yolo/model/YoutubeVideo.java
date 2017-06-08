package com.android.capstone.yolo.model;

/**
 * Created by sung9 on 2017-06-01.
 */

public class YoutubeVideo {
    private String videoId, title, thumbnail;
    private int viewCount, likeCount;
    private String[] like;

    public void setViewCount(int _viewCount){
        viewCount = _viewCount;
    }
    public void setLikeCount(int _likeCount){
        likeCount = _likeCount;
    }
    public void setLike(String[] _like){
        like = _like;
    }
    public int getViewCount(){
        return viewCount;
    }
    public int getLikeCount(){
        return likeCount;
    }
    public String[] getLike(){
        return like;
    }
    public void setVideoId(String _videoId){
        videoId = _videoId;
    }
    public String getVideoId(){
        return videoId;
    }
    public void setTitle(String _title){
        title = _title;
    }
    public String getTitle(){
        return title;
    }
    public void setThumbnail(String _thumbnail){
        thumbnail = _thumbnail;
    }
    public String getThumbnail(){
        return thumbnail;
    }
}
