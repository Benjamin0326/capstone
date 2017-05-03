package com.android.capstone.yolo.layer.festival;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.FestivalPictureAdapter;
import com.android.capstone.yolo.adapter.FestivalVideoAdapter;

public class FestivalTabVideoFragment extends Fragment {
    String[] addr = {"iQpGq4HguVs",
            "pFscrs6qmDY",
            "PfQhLcYqE88", "4r1tq1XBN0w"};
    Uri[] uri;

    private FestivalVideoAdapter adapter;
    private RecyclerView recyclerView;

    public FestivalTabVideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_festival_tab_video, container, false);
        /*uri = new Uri[addr.length];
        for(int i=0;i<addr.length;i++){
            uri[i] = Uri.parse(addr[i]);
        }
        */
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_festival_tab_video);
        adapter = new FestivalVideoAdapter(getContext(), addr);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        return rootView;
    }

}
