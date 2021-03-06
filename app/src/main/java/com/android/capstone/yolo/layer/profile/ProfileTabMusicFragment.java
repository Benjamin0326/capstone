package com.android.capstone.yolo.layer.profile;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.capstone.yolo.MainActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.MusicSearchResultAdapter;
import com.android.capstone.yolo.adapter.ProfileMusicAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.layer.community.SimpleDividerItemDecoration;
import com.android.capstone.yolo.model.Music;
import com.android.capstone.yolo.model.YoutubeVideo;
import com.android.capstone.yolo.service.MusicService;
import com.android.capstone.yolo.service.ProfileService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTabMusicFragment extends Fragment {

    private MusicSearchResultAdapter adapter;
    private RecyclerView recyclerView;
    private List<YoutubeVideo> music;
    String[] addr = {"iQpGq4HguVs",
            "pFscrs6qmDY",
            "PfQhLcYqE88", "4r1tq1XBN0w"};
    private String[] img_music = {"https://upload.wikimedia.org/wikipedia/commons/thumb/1/13/Girl_Talk_Producing_Live.jpg/250px-Girl_Talk_Producing_Live.jpg",
    "https://upload.wikimedia.org/wikipedia/commons/thumb/1/13/Girl_Talk_Producing_Live.jpg/250px-Girl_Talk_Producing_Live.jpg",
    "https://upload.wikimedia.org/wikipedia/commons/thumb/1/13/Girl_Talk_Producing_Live.jpg/250px-Girl_Talk_Producing_Live.jpg",
    "https://upload.wikimedia.org/wikipedia/commons/thumb/1/13/Girl_Talk_Producing_Live.jpg/250px-Girl_Talk_Producing_Live.jpg"};
    private Uri[] img_uri_music;
    private String[] music_name = {"A Music", "B Music", "C Music", "D Music"};
    private String[][] music_tag = { {"ABC"}, {"ABC", "DEF"}, {"ABC", "DEF", "GHI"}, {"ABC", "DEF", "GHI", "JKL"}};

    public ProfileTabMusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile_tab_music, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_profile_tab_music);

        /*
        img_uri_music = new Uri[img_music.length];
        for(int i=0;i<img_music.length;i++){
            img_uri_music[i] = Uri.parse(img_music[i]);
        }
        */
        adapter = new MusicSearchResultAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        recyclerView.setLayoutManager(layoutManager);
        getMyLikeMusicChart();
        return rootView;
    }
    public void getMyLikeMusicChart(){
        ProfileService service = network.buildRetrofit().create(ProfileService.class);
        Call<List<YoutubeVideo>> musicChartListCall = service.getMyLikeMusic(MainActivity.token);

        musicChartListCall.enqueue(new Callback<List<YoutubeVideo>>() {
            @Override
            public void onResponse(Call<List<YoutubeVideo>> call, Response<List<YoutubeVideo>> response) {
                if(response.isSuccessful()){
                    music = response.body();
                    if(music.size()!=0){
                        for(int i=0;i<music.size();i++)
                            music.get(i).setLike(2);
                    }
                    adapter.setSource(music);

                    //for(int i=0;i<festivalLists.get(position).getVideo().length;i++){
                    //    Log.d("#Test :", festivalLists.get(position).getVideo()[i]);
                    //}
                    //Picasso.with(getActivity()).load(festivalLists.get(position).getImg()[1]).into(img);
                    return;
                }
                int code = response.code();
                Log.d("TEST", "err code : " + code);
            }

            @Override
            public void onFailure(Call<List<YoutubeVideo>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to load", Toast.LENGTH_LONG).show();
                Log.i("TEST","err : "+ t.getMessage());
            }
        });
    }
}
