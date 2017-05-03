package com.android.capstone.yolo.service;

import android.database.Observable;

import com.android.capstone.yolo.model.CommunityList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FestivalService {

    @PUT("/notifications/{id}/read")
    Observable<Void> read(@Path("id") long id);

    @GET("/api/board/type")
    Call<List<CommunityList>> getCommunityList();
}
