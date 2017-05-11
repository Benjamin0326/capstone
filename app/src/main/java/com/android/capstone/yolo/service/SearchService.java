package com.android.capstone.yolo.service;

import com.android.capstone.yolo.model.BoardList;
import com.android.capstone.yolo.model.SearchResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SearchService {

    @GET("/api/board")
    Call<SearchResult> search(@Path("query") String query);

    @GET("/api/board")
    Call<List<BoardList>> tempSearch();
}
