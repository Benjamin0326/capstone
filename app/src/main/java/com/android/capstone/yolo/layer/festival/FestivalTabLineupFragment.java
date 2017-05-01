package com.android.capstone.yolo.layer.festival;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.FestivalLineupAdapter;
import com.android.capstone.yolo.adapter.FestivalPictureAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FestivalTabLineupFragment extends Fragment {
    String[] lineUp = {"A DJ", "B DJ", "C DJ", "D DJ", "ETC"};
    private FestivalLineupAdapter adapter;
    private RecyclerView recyclerView;
    public FestivalTabLineupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_festival_tab_lineup, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_festival_tab_lineup);
        adapter = new FestivalLineupAdapter(getContext(), lineUp);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        return rootView;
    }

}
