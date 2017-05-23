package com.android.capstone.yolo.service;

import com.android.capstone.yolo.model.CommunityList;
import com.android.capstone.yolo.model.FestivalList;
import com.android.capstone.yolo.model.VideoAuthor;
import com.android.capstone.yolo.model.VideoInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sung9 on 2017-05-22.
 */

public interface VideoInfoService {
    @GET("/oembed")
    Call<VideoAuthor> getAuthor(@Query("url") String name);

    @GET("/youtube/v3/videos")
    Call<VideoInfo> getVideoInfo(@Query("id") String videoId, @Query("key") String apiKey, @Query("part") String part, @Query("fields") String field);
}
//"snippet,statistics"
//"items(id,snippet,statistics)"