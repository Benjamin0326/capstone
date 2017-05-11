package com.android.capstone.yolo.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.android.capstone.yolo.MainFragment;
import com.android.capstone.yolo.layer.festival.ItemFestivalFragment;
import com.android.capstone.yolo.model.FestivalList;

import java.util.List;

/**
 * Created by sung9 on 2017-04-19.
 */

public class FestivalListAdapter extends FragmentStatePagerAdapter {
    Context context;
    List<FestivalList> festivalLists;

    public FestivalListAdapter(FragmentManager fm, Context _context, List<FestivalList> _festivalLists) {
        super(fm);
        context = _context;
        festivalLists = _festivalLists;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fr = new ItemFestivalFragment();
        Bundle args = new Bundle();
        args.putInt("ImageResource", position);
        args.putStringArray("image", festivalLists.get(position).getImg());
        //Log.i("FestivalPosterImageTest", festivalLists.get(position).getImageList().length+"");
        fr.setArguments(args);
        return fr;
    }

    @Override
    public int getCount() {
        if(festivalLists==null)
            return 0;
        else
            return festivalLists.size();
    }
}
