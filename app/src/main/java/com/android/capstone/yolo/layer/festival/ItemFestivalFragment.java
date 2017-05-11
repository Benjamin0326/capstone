package com.android.capstone.yolo.layer.festival;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.capstone.yolo.MainFragment;
import com.android.capstone.yolo.R;
import com.squareup.picasso.Picasso;


public class ItemFestivalFragment extends Fragment {

    private ImageView img;

    public ItemFestivalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_item_festival, container, false);
        img = (ImageView) rootView.findViewById(R.id.img_item_festival);
        int id = getArguments().getInt("ImageResource", R.drawable.festival1);
        String[] imageResource = getArguments().getStringArray("image");

        if(imageResource!=null) {
            Picasso.with(getActivity()).load(imageResource[0]).into(img);
        }

        return rootView;
    }

}
