package com.android.capstone.yolo.service;

import com.android.capstone.yolo.model.Music;
import com.android.capstone.yolo.model.VideoAuthor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sung9 on 2017-05-24.
 */

public interface MusicService {
    @GET("/api/rank")
    Call<List<Music>> getRank();

}
