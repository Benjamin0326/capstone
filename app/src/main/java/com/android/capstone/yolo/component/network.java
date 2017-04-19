package com.android.capstone.yolo.component;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class network extends Application {

    public static Retrofit buildRetrofit() {

        Retrofit.Builder builder = new Retrofit.Builder();

        builder.baseUrl("https://api.github.com/");
        builder.addConverterFactory(GsonConverterFactory.create());

        return builder.build();
    }
}
