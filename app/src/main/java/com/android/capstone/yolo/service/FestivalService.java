package com.android.capstone.yolo.service;

import android.database.Observable;

import com.android.capstone.yolo.model.CommunityList;
import com.android.capstone.yolo.model.FestivalList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FestivalService {

    @PUT("/notifications/{id}/read")
    Observable<Void> read(@Path("id") long id);

    @GET("/api/festival")
    Call<List<CommunityList>> getCommunityList();

    @GET("/api/festival")
    Call<List<FestivalList>> getFestival(@Query("access_token") String token);
}
