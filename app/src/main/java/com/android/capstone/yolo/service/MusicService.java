package com.android.capstone.yolo.service;

import com.android.capstone.yolo.model.Music;
import com.android.capstone.yolo.model.VideoAuthor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sung9 on 2017-05-24.
 */

public interface MusicService {
    @GET("/api/rank")
    Call<List<Music>> getRank();

    @POST("/api/rank/like/{id}")
    Call<Music> postLike(@Path("id") String musicId, @Query("access_token") String token);

    @GET("/api/rank/my")
    Call<List<Music>> getMusicLike(@Query("access_token") String token);
}
