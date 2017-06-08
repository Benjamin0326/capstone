package com.android.capstone.yolo.model;

/**
 * Created by sung9 on 2017-06-01.
 */

public class YoutubeVideo {
    private String videoId, title, thumbnail;
    private int viewCount, likeCount, isLiked;

    public void setViewCount(int _viewCount){
        viewCount = _viewCount;
    }
    public void setLikeCount(int _likeCount){
        likeCount = _likeCount;
    }
    public void setLike(int _like){
        isLiked = _like;
    }
    public int getViewCount(){
        return viewCount;
    }
    public int getLikeCount(){
        return likeCount;
    }
    public int getLike(){
        return isLiked;
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
