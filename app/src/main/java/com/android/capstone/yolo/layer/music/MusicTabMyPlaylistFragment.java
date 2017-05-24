package com.android.capstone.yolo.layer.music;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.FestivalPagerAdapter;
import com.android.capstone.yolo.adapter.MusicPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicTabMyPlaylistFragment extends Fragment {


    public MusicTabMyPlaylistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_music_tab_my_playlist, container, false);


        return rootView;
    }

}
