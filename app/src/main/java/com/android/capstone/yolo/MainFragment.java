package com.android.capstone.yolo;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private int[] img_festival = {R.drawable.festival1, R.drawable.festival2, R.drawable.festival3};
    private FestivalAdapter adapter;
    private RecyclerView recycler;
    private LinearLayoutManager layoutManager;

    public MainFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        recycler = (RecyclerView) rootView.findViewById(R.id.recycler_festival_list);
        adapter = new FestivalAdapter(getActivity(), img_festival);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(layoutManager);
        return rootView;
    }

}
