package com.android.capstone.yolo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.capstone.yolo.layer.community.CommunityBoardAllFragment;
import com.android.capstone.yolo.layer.community.CommunityBoardPopularFragment;

public class CommunityPagerAdapter extends FragmentStatePagerAdapter{
    CommunityBoardAllFragment allFragment;
    CommunityBoardPopularFragment popularFragment;
    private int tabCount;

    public CommunityPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if(allFragment == null)
                    allFragment = new CommunityBoardAllFragment();
                return allFragment;
            case 1:
                if(popularFragment == null)
                    popularFragment = new CommunityBoardPopularFragment();
                return popularFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    public CommunityBoardAllFragment getAllFragment(){
        return allFragment;
    }
}
