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

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.FestivalVideoAdapter;
import com.android.capstone.yolo.adapter.MusicRankAdapter;
import com.android.capstone.yolo.adapter.ProfileMusicAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.model.FestivalList;
import com.android.capstone.yolo.model.Music;
import com.android.capstone.yolo.service.FestivalService;
import com.android.capstone.yolo.service.MusicService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicTabRankFragment extends Fragment {
    private MusicRankAdapter adapter;
    private RecyclerView recyclerView;
    private List<Music> music;

    public MusicTabRankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_music_tab_rank, container, false);
        adapter = new MusicRankAdapter(rootView.getContext(), music);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_tab_music_rank);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        getMusicChart();
        return rootView;
    }

    public void getMusicChart(){
        MusicService service = network.buildRetrofit().create(MusicService.class);
        Call<List<Music>> musicChartListCall = service.getRank();

        musicChartListCall.enqueue(new Callback<List<Music>>() {
            @Override
            public void onResponse(Call<List<Music>> call, Response<List<Music>> response) {
                if(response.isSuccessful()){
                    music = response.body();
                    adapter = new MusicRankAdapter(getContext(), music);

                    recyclerView.setAdapter(adapter);
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
            public void onFailure(Call<List<Music>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to load thumbnails", Toast.LENGTH_LONG).show();
                Log.i("TEST","err : "+ t.getMessage());
            }
        });
    }

}

