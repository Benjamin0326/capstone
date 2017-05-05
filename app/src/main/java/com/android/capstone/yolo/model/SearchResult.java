package com.android.capstone.yolo.model;

import java.util.List;

public class SearchResult {

    private List<BoardList> boardLists;

    public List<BoardList> getBoardLists() { return boardLists; }
    public void setBoardLists(List<BoardList> boardLists) { this.boardLists = boardLists; }

    private List<Music> musics;

    public List<Music> getMusics() { return musics; }
    public void setMusics(List<Music> musics) { this.musics = musics; }
}
