package com.android.capstone.yolo.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.capstone.yolo.layer.festival.ItemFestivalFragment;

/**
 * Created by sung9 on 2017-04-19.
 */

public class FestivalListAdapter extends FragmentStatePagerAdapter {
    int numOfFragments;
    int[] imageResources;
    Context context;

    public FestivalListAdapter(FragmentManager fm, int _numOfFragments, int[] _imageResources, Context _context) {
        super(fm);
        numOfFragments = _numOfFragments;
        imageResources = _imageResources;
        context = _context;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fr = new ItemFestivalFragment();
        Bundle args = new Bundle();
        args.putInt("ImageResource", imageResources[position]);
        fr.setArguments(args);
        return fr;
    }

    @Override
    public int getCount() {
        return numOfFragments;
    }
}
