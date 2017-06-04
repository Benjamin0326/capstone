package com.android.capstone.yolo.service;

import com.android.capstone.yolo.model.Post;
import com.android.capstone.yolo.model.Profile;
import com.android.capstone.yolo.model.Reply;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sung9 on 2017-05-24.
 */

public interface ProfileService {
    @GET("/api/board/user/{id}")
    Call<List<Post>> getMyBoard(@Path("id") String id);

    @GET("/api/comment/user/{id}")
    Call<List<Reply>> getMyComment(@Path("id") String id);

    @GET("/api/user/{id}")
    Call<Profile> getProfile(@Path("id") String id);
}
