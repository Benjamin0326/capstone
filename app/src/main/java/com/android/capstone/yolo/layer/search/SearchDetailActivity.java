package com.android.capstone.yolo.layer.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.MusicSearchResultAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.layer.community.SimpleDividerItemDecoration;
import com.android.capstone.yolo.model.YoutubeVideo;
import com.android.capstone.yolo.service.MusicService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchDetailActivity extends AppCompatActivity{
    TextView title;
    MusicSearchResultAdapter adapter;
    RecyclerView musicList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        initView();
    }

    public void initView(){
        String music = getIntent().getExtras().getString("musicID");
        musicList = (RecyclerView) findViewById(R.id.music_result_detail);
        adapter = new MusicSearchResultAdapter(getApplicationContext());
        musicList.setAdapter(adapter);
        musicList.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
        musicList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        getMusicResult(music);
    }

    public void getMusicResult(String music){
        MusicService service = network.buildRetrofit().create(MusicService.class);
        Call<List<YoutubeVideo>> musicChartListCall = service.getSearchMusic(music);

        musicChartListCall.enqueue(new Callback<List<YoutubeVideo>>() {
            @Override
            public void onResponse(Call<List<YoutubeVideo>> call, Response<List<YoutubeVideo>> response) {
                if(response.isSuccessful()){
                    adapter.setSource(response.body());
                    return;
                }
                Log.d("TEST", "err " + response.code() + " : " + response.message());
            }

            @Override
            public void onFailure(Call<List<YoutubeVideo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to search Youtube Video", Toast.LENGTH_LONG).show();
                Log.i("TEST","err : "+ t.getMessage());
            }
        });
    }
}
