package com.android.capstone.yolo.layer.profile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.capstone.yolo.MainActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.MusicSearchResultAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.layer.community.SimpleDividerItemDecoration;
import com.android.capstone.yolo.model.ProfileImage;
import com.android.capstone.yolo.model.YoutubeVideo;
import com.android.capstone.yolo.service.MusicService;
import com.android.capstone.yolo.service.ProfileService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {

    private TextView user_name, noResult;
    private RecyclerView recyclerView;
    private MusicSearchResultAdapter adapter;
    private List<YoutubeVideo> music;
    String strId, strName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user_name = (TextView) findViewById(R.id.user_profile_title);
        noResult = (TextView) findViewById(R.id.user_profile_no_list);
        recyclerView = (RecyclerView) findViewById(R.id.user_profile_recycler);

        adapter = new MusicSearchResultAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        strId = getIntent().getExtras().getString("userId");
        //user_name.setText();
        getUserProfile(strId);
    }

    public void getUserProfile(String id){

        ProfileService service = network.buildRetrofit().create(ProfileService.class);
        Call<ProfileImage> call = service.getProfile(id, MainActivity.token);
        call.enqueue(new Callback<ProfileImage>() {
            @Override
            public void onResponse(Call<ProfileImage> call, final Response<ProfileImage> response) {
                if(response.isSuccessful()) {
                    strName = response.body().getName();
                    user_name.setText(strName+" 님이 좋아요한  노래");
                    getMyLikeMusicChart(strName);
                    //Picasso.with(getApplicationContext()).load(response.body().getImage()).into(user_profile);
                    /*
                    CircleImageView.OnClickListener img_listener = new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                            intent.putExtra("userId", response.body().get_id());
                            startActivity(intent);
                        }
                    };

                    user_profile.setOnClickListener(img_listener);
                    */
                    return;
                }
                Toast.makeText(getApplicationContext(), "Profile Image err " + response.code() + " : " + response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ProfileImage> call, Throwable t) {
                Log.d("TEST", "err msg : " + t.getMessage().toString());
            }
        });
    }

    public void getMyLikeMusicChart(String name){
        MusicService service = network.buildRetrofit().create(MusicService.class);
        Call<List<YoutubeVideo>> musicChartListCall = service.getUserMusicLike(name, MainActivity.token);

        musicChartListCall.enqueue(new Callback<List<YoutubeVideo>>() {
            @Override
            public void onResponse(Call<List<YoutubeVideo>> call, Response<List<YoutubeVideo>> response) {
                if(response.isSuccessful()){
                    music = response.body();
                    if(music.size()==0){
                        recyclerView.setVisibility(View.GONE);
                        noResult.setVisibility(View.VISIBLE);
                        return;
                    }
                    for(int i=0;i<music.size();i++)
                        music.get(i).setLike(2);

                    recyclerView.setVisibility(View.VISIBLE);
                    noResult.setVisibility(View.GONE);
                    adapter.setSource(music);
                    return;
                }
                Log.d("TEST", "err " + response.code() + " : " + response.message());
            }

            @Override
            public void onFailure(Call<List<YoutubeVideo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to load", Toast.LENGTH_LONG).show();
                Log.i("TEST","err : "+ t.getMessage());
            }
        });
    }

}