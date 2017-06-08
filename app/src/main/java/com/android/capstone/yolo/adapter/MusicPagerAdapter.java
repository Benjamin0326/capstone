package com.android.capstone.yolo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.capstone.yolo.layer.music.MusicTabMyPlaylistFragment;
import com.android.capstone.yolo.layer.music.MusicTabRankFragment;

/**
 * Created by sung9 on 2017-05-24.
 */

public class MusicPagerAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;

    public MusicPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MusicTabRankFragment tab1 = new MusicTabRankFragment();
                return tab1;
            case 1:
                MusicTabMyPlaylistFragment tab2 = new MusicTabMyPlaylistFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}