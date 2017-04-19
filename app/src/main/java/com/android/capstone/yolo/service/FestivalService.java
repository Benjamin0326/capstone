package com.android.capstone.yolo.service;

import android.database.Observable;

import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FestivalService {

    @PUT("/notifications/{id}/read")
    Observable<Void> read(@Path("id") long id);

}
