package com.android.capstone.yolo.service;

import com.android.capstone.yolo.model.BoardList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SearchService {

    @GET("/api/board/search/{query}")
    Call<List<BoardList>> search(@Path("query") String query, @Query("access_token") String token);
}
