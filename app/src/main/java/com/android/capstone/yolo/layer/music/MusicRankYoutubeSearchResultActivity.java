package com.android.capstone.yolo.layer.music;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.capstone.yolo.MainActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.MusicSearchResultAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.model.YoutubeVideo;
import com.android.capstone.yolo.service.MusicService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicRankYoutubeSearchResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MusicSearchResultAdapter adapter;
    private List<YoutubeVideo> music;
    private String artist, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_rank_youtube_search_result);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_music_rank_search_result);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new MusicSearchResultAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        artist = intent.getExtras().getString("artist");
        title = intent.getExtras().getString("title");

        getYoutubeVideoId(artist, title);
    }
    public void getYoutubeVideoId(String artist, String title){
        //Log.d("Music ID & Token : ", music.get(pos).get_id()+" "+MainActivity.token);
        MusicService service = network.buildRetrofit().create(MusicService.class);
        Call<List<YoutubeVideo>> videoIdCall = service.getVideoId(artist, title, MainActivity.token);

        videoIdCall.enqueue(new Callback<List<YoutubeVideo>>() {
            @Override
            public void onResponse(Call<List<YoutubeVideo>> call, Response<List<YoutubeVideo>> response) {
                if(response.isSuccessful()){
                    List<YoutubeVideo> tmp_video = response.body();
                    if(tmp_video.size()==0){
                        Toast.makeText(MusicRankYoutubeSearchResultActivity.this, "Youtube 비디오를 찾을 수 없습니다.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    //Log.d("Youtube Video : ", tmp_video.getVideoId());
                    adapter.setSource(tmp_video);
                    //Toast.makeText(context, "좋아요가 반영되었습니다.", Toast.LENGTH_LONG).show();
                    //String[] tmp = {"1"};
                    //music.get(pos).setLike(tmp);

                    //for(int i=0;i<festivalLists.get(position).getVideo().length;i++){
                    //    Log.d("#Test :", festivalLists.get(position).getVideo()[i]);
                    //}
                    //Picasso.with(getActivity()).load(festivalLists.get(position).getImg()[1]).into(img);
                    return;
                }
                Log.d("TEST", "err " + response.code() + " : " + response.message());
            }

            @Override
            public void onFailure(Call<List<YoutubeVideo>> call, Throwable t) {
                Toast.makeText(MusicRankYoutubeSearchResultActivity.this, "Failed to Like", Toast.LENGTH_LONG).show();
                Log.i("TEST","err : "+ t.getMessage());
            }
        });
    }
}
