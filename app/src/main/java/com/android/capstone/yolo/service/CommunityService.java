package com.android.capstone.yolo.service;

import android.database.Observable;

import com.android.capstone.yolo.model.BoardList;
import com.android.capstone.yolo.model.Post;
import com.android.capstone.yolo.model.Reply;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CommunityService {

    @GET("/notifications/{id}/read")
    Observable<Void> read(@Path("id") long id);

    @GET("/api/board/festival/{id}")
    Call<List<BoardList>> getBoardList(@Path("id") String id);

    @GET("/api/board/{id}")
    Call<Post> getBoardDetail(@Path("id") String id);

    @GET("/api/board/comment/{id}")
    Call<List<Reply>> getReply(@Path("id") String id);
}
