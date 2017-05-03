package com.android.capstone.yolo.service;

import android.database.Observable;

import com.android.capstone.yolo.model.BoardList;
import com.android.capstone.yolo.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CommunityService {

    @GET("/notifications/{id}/read")
    Observable<Void> read(@Path("id") long id);

    @GET("/api/board/{id}")
    Call<List<BoardList>> getBoardList(@Path("id") long id);

    @GET("/api/board/detail/{id}")
    Call<Post> getBoardDetail(@Path("id") long id);
}
