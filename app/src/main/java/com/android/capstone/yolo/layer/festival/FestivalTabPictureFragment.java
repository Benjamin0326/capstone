package com.android.capstone.yolo.layer.festival;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.capstone.yolo.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FestivalTabPictureFragment extends Fragment {


    public FestivalTabPictureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_festival_tab_picture, container, false);
    }

}
