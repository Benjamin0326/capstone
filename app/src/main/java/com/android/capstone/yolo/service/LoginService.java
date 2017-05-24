package com.android.capstone.yolo.service;

import com.android.capstone.yolo.model.Login;
import com.android.capstone.yolo.model.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by sung9 on 2017-05-23.
 */

public interface LoginService {
    @FormUrlEncoded
    @POST("/api/user/login")
    Call<Login> postLogin(@Field("name") String name, @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/api/user/login")
    Call<Login> postLogin2(UserInfo userInfo);

    @FormUrlEncoded
    @POST("/api/user")
    Call<Login> postJoin(@Field("name") String name, @Field("email") String email, @Field("password") String password);
}
