package com.android.capstone.yolo.component;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class network extends Application {

    public static Retrofit buildRetrofit() {

        Retrofit.Builder builder = new Retrofit.Builder();

        builder.baseUrl("http://ec2-52-79-215-229.ap-northeast-2.compute.amazonaws.com");
        builder.addConverterFactory(GsonConverterFactory.create());

        return builder.build();
    }
}
