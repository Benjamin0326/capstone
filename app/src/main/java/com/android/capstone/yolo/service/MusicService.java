package com.android.capstone.yolo.service;

import com.android.capstone.yolo.model.Music;
import com.android.capstone.yolo.model.VideoAuthor;
import com.android.capstone.yolo.model.YoutubeVideo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
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
    Call<List<Music>> getRank(@Query("access_token") String token);

    @GET("/api/rank/video/{artist}/{title}")
    Call<List<YoutubeVideo>> getVideoId(@Path("artist") String artist, @Path("title") String title, @Query("access_token") String token);

    @POST("/api/rank/like/{id}")
    Call<Music> postLike(@Path("id") String musicId, @Query("access_token") String token);

    @DELETE("/api/rank/like/{id}")
    Call<Music> deleteLike(@Path("id") String musicId, @Query("access_token") String token);

    @GET("/api/rank/my")
    Call<List<Music>> getMusicLike(@Query("access_token") String token);

    @GET("/api/user/music/{name}")
    Call<List<YoutubeVideo>> getUserMusicLike(@Path("name") String name, @Query("access_token") String token);

    @GET("/api/rank/videosearch/{query}")
    Call<List<YoutubeVideo>> getSearchMusic(@Path("query") String query, @Query("access_token") String token);

    @POST("/api/music/insert/{id}")
    Call<YoutubeVideo> insertMusic(@Path("id") String id, @Query("access_token") String token);

    @POST("/api/user/like/{id}")
    Call<YoutubeVideo> iLikeIt(@Path("id") String id, @Query("access_token") String token);
}
