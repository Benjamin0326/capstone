package com.android.capstone.yolo.adapter;

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

    public FestivalPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FestivalTabInfoFragment tab1 = new FestivalTabInfoFragment(); // Fragment 는 알아서 만들자
                return tab1;
            case 1:
                FestivalTabPictureFragment tab2 = new FestivalTabPictureFragment();
                return tab2;
            case 2:
                FestivalTabVideoFragment tab3 = new FestivalTabVideoFragment();
                return tab3;
            case 3:
                FestivalTabLineupFragment tab4 = new FestivalTabLineupFragment();
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
