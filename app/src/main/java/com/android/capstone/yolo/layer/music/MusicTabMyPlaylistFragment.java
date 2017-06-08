package com.android.capstone.yolo.layer.music;


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
import com.android.capstone.yolo.adapter.MusicLikeRankAdapter;
import com.android.capstone.yolo.adapter.MusicSearchResultAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.layer.community.SimpleDividerItemDecoration;
import com.android.capstone.yolo.layer.profile.ProfileFragment;
import com.android.capstone.yolo.model.Music;
import com.android.capstone.yolo.model.YoutubeVideo;
import com.android.capstone.yolo.service.MusicService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicTabMyPlaylistFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<YoutubeVideo> music;
    private MusicSearchResultAdapter adapter;

    public MusicTabMyPlaylistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_music_tab_my_playlist, container, false);
        adapter = new MusicSearchResultAdapter(rootView.getContext());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_tab_my_like_music);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        getMyLikeMusicChart();

        return rootView;
    }

    public void getMyLikeMusicChart(){
        MusicService service = network.buildRetrofit().create(MusicService.class);
        Call<List<YoutubeVideo>> musicChartListCall = service.getUserMusicLike(ProfileFragment.userName, MainActivity.token);

        musicChartListCall.enqueue(new Callback<List<YoutubeVideo>>() {
            @Override
            public void onResponse(Call<List<YoutubeVideo>> call, Response<List<YoutubeVideo>> response) {
                if(response.isSuccessful()){
                    music = response.body();
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
                Toast.makeText(getActivity(), "Failed to load Recommends", Toast.LENGTH_LONG).show();
                Log.i("TEST","err : "+ t.getMessage());
            }
        });
    }

}
