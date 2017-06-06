package com.android.capstone.yolo.service;

import com.android.capstone.yolo.model.Post;
import com.android.capstone.yolo.model.Profile;
import com.android.capstone.yolo.model.ProfileImage;
import com.android.capstone.yolo.model.ProfileImageByName;
import com.android.capstone.yolo.model.Reply;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sung9 on 2017-05-24.
 */

public interface ProfileService {
    @Multipart
    @POST("/api/user/image")
    Call<ProfileImage> postUserImage(@PartMap Map<String, RequestBody> params, @Query("access_token") String token);

    @GET("/api/user/me")
    Call<ProfileImage> getUserImage(@Query("access_token") String token);

    @GET("/api/user/username/{id}")
    Call<ProfileImageByName> getUserImageByName(@Path("id") String id, @Query("access_token") String token);

    @GET("/api/board/user/{id}")
    Call<List<Post>> getMyBoard(@Path("id") String id, @Query("access_token") String token);

    @GET("/api/comment/user/{id}")
    Call<List<Reply>> getMyComment(@Path("id") String id, @Query("access_token") String token);

    @GET("/api/user/{id}")
    Call<ProfileImage> getProfile(@Path("id") String id, @Query("access_token") String token);
}
