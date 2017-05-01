package com.android.capstone.yolo.layer.festival;


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


/**
 * A simple {@link Fragment} subclass.
 */
public class FestivalTabPictureFragment extends Fragment {
    String[] addr = {"http://www.flooringvillage.co.uk/ekmps/shops/flooringvillage/images/request-a-sample--547-p.jpg",
    "http://inspectiondoc.com/wp-content/uploads/2014/08/sample-icon.png",
    "https://thumb9.shutterstock.com/display_pic_with_logo/436114/274833056/stock-vector-sample-grunge-retro-red-isolated-ribbon-stamp-sample-stamp-sample-sample-sign-274833056.jpg"};

    Uri[] uri;

    private FestivalPictureAdapter adapter;
    private RecyclerView recyclerView;
    public FestivalTabPictureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        uri = new Uri[addr.length];
        for(int i=0;i<addr.length;i++){
            uri[i] = Uri.parse(addr[i]);
        }
        View rootView =  inflater.inflate(R.layout.fragment_festival_tab_picture, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_festival_tab_picture);
        adapter = new FestivalPictureAdapter(getContext(), uri);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        return rootView;
    }

}
