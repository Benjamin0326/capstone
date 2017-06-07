package com.android.capstone.yolo.service;

import android.database.Observable;

import com.android.capstone.yolo.model.BoardImage;
import com.android.capstone.yolo.model.BoardList;
import com.android.capstone.yolo.model.Post;
import com.android.capstone.yolo.model.Reply;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CommunityService {

    @GET("/notifications/{id}/read")
    Observable<Void> read(@Path("id") long id);

    @GET("/api/board/festival/{id}")
    Call<List<BoardList>> getBoardList(@Path("id") String id, @Query("access_token") String token);

    @GET("/api/board/{id}")
    Call<Post> getBoardDetail(@Path("id") String id, @Query("access_token") String token);

    @GET("/api/comment/{id}")
    Call<List<Reply>> getReply(@Path("id") String id, @Query("access_token") String token);

    @FormUrlEncoded
    @POST("/api/comment/{id}")
    Call<Void> postReply(@Path("id") String id, @Field("content") String content, @Query("access_token") String token);

    @FormUrlEncoded
    @POST("/api/board")
    Call<BoardList> postText(@Field("type") String type, @Field("tag") String tag, @Field("title") String title, @Field("content") String content, @Query("access_token") String token);

    @Multipart
    @POST("/api/board/image/{id}")
    Call<BoardImage> postImage(@Path("id") String id, @PartMap Map<String, RequestBody> params, @Query("access_token") String token);

}
