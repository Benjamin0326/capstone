package com.android.capstone.yolo.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.capstone.yolo.layer.festival.FestivalTabInfoFragment;
import com.android.capstone.yolo.layer.festival.FestivalTabLineupFragment;
import com.android.capstone.yolo.layer.festival.FestivalTabPictureFragment;
import com.android.capstone.yolo.layer.festival.FestivalTabVideoFragment;

/**
 * Created by sung9 on 2017-04-19.
 */

public class FestivalPagerAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;
    int pos;

    public FestivalPagerAdapter(FragmentManager fm, int numOfTabs, int _position) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.pos = _position;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args;
        switch (position) {
            case 0:
                 args = new Bundle();
                args.putInt("position", pos);
                FestivalTabInfoFragment tab1 = new FestivalTabInfoFragment(); // Fragment 는 알아서 만들자
                tab1.setArguments(args);
                return tab1;
            case 1:
                args = new Bundle();
                args.putInt("position", pos);
                FestivalTabPictureFragment tab2 = new FestivalTabPictureFragment();
                tab2.setArguments(args);
                return tab2;
            case 2:
                args = new Bundle();
                args.putInt("position", pos);
                FestivalTabVideoFragment tab3 = new FestivalTabVideoFragment();
                tab3.setArguments(args);
                return tab3;
            case 3:
                args = new Bundle();
                args.putInt("position", pos);
                FestivalTabLineupFragment tab4 = new FestivalTabLineupFragment();
                tab4.setArguments(args);
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
