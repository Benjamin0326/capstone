package com.android.capstone.yolo.model;

import java.util.List;

/**
 * Created by sung9 on 2017-05-22.
 */

public class VideoInfo {
    public List<Videos> videos;

    public class Videos{
        String id;
        public Snippet snippet;
        public Statistics statistics;

        public String getId(){
            return id;
        }

        public void setId(String _id){
            id = _id;
        }
        public class Snippet{
            String publishedAt, channelId, title, description;
            public String getPublishedAt(){
                return publishedAt;
            }
            public String getChannelId(){
                return channelId;
            }
            public String getTitle(){
                return title;
            }
            public String getDescription(){
                return description;
            }
            public void setPublishedAt(String _publishedAt){
                publishedAt = _publishedAt;
            }
            public void setChannelId(String _channelId){
                channelId = _channelId;
            }
            public void setTitle(String _title){
                title = _title;
            }
            public void setDescription(String _description){
                description = _description;
            }
        }

        public class Statistics{
            String viewCount, likeCount, dislikeCount, favoriteCount, commentCount;
            public String getViewCount(){
                return viewCount;
            }
            public String getLikeCount(){
                return likeCount;
            }
            public String getDislikeCount(){
                return dislikeCount;
            }
            public String getFavoriteCount(){
                return favoriteCount;
            }
            public String getCommentCount(){
                return commentCount;
            }
            public void setViewCount(String _viewCount){
                viewCount = _viewCount;
            }
            public void setLikeCount(String _likeCount){
                likeCount = _likeCount;
            }
            public void setDislikeCount(String _dislikeCount){
                dislikeCount = _dislikeCount;
            }
            public void setFavoriteCount(String _favoriteCount){
                favoriteCount = _favoriteCount;
            }
            public void setCommentCount(String _commentCount){
                commentCount = _commentCount;
            }
        }
    }
}

