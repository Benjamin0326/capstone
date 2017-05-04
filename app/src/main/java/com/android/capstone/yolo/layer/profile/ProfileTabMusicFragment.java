package com.android.capstone.yolo.layer.profile;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.ProfileMusicAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTabMusicFragment extends Fragment {

    private ProfileMusicAdapter adapter;
    private RecyclerView recyclerView;
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

        img_uri_music = new Uri[img_music.length];
        for(int i=0;i<img_music.length;i++){
            img_uri_music[i] = Uri.parse(img_music[i]);
        }

        adapter = new ProfileMusicAdapter(getContext(), img_uri_music, music_name, music_tag);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        return rootView;
    }

}
