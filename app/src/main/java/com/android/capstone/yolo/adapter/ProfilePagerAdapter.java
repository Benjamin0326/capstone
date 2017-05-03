package com.android.capstone.yolo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.capstone.yolo.layer.festival.FestivalTabLineupFragment;
import com.android.capstone.yolo.layer.profile.ProfileTabMusicFragment;
import com.android.capstone.yolo.layer.profile.ProfileTabReplyFragment;
import com.android.capstone.yolo.layer.profile.ProfileTabWritingFragment;

/**
 * Created by sung9 on 2017-05-03.
 */

public class ProfilePagerAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;

    public ProfilePagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProfileTabMusicFragment tab1 = new ProfileTabMusicFragment(); // Fragment 는 알아서 만들자
                return tab1;
            case 1:
                ProfileTabWritingFragment tab2 = new ProfileTabWritingFragment();
                return tab2;
            case 2:
                ProfileTabReplyFragment tab3 = new ProfileTabReplyFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}