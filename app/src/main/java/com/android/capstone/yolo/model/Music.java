package com.android.capstone.yolo.model;

/**
 * Created by kyungjoo on 2017. 5. 5..
 */

public class Music {
    private String title, artist, albumtitle, albumcover, rank;
    public void setRank(String _rank){
        rank = _rank;
    }
    public String getRank(){
        return rank;
    }
    public void setTitle(String _title){
        title = _title;
    }
    public void setArtist(String _artist){
        artist = _artist;
    }
    public void setAlbumtitle(String _albumTitle){
        albumtitle = _albumTitle;
    }
    public void setAlbumcover(String _albumcover){
        albumcover = _albumcover;
    }
    public String getTitle(){
        return title;
    }
    public String getArtist(){
        return artist;
    }
    public String getAlbumtitle(){
        return albumtitle;
    }
    public String getAlbumcover(){
        return albumcover;
    }
}
