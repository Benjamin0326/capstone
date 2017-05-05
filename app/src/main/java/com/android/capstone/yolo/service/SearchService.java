package com.android.capstone.yolo.service;

import com.android.capstone.yolo.model.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SearchService {

    @GET("/api/board/search/{query}")
    Call<SearchResult> search(@Path("query") String query);
}
