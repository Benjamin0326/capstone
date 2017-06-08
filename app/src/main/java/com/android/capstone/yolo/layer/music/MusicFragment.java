package com.android.capstone.yolo.layer.music;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.MusicPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MusicPagerAdapter pagerAdapter;

    public MusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_music, container, false);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_music);
        tabLayout.addTab(tabLayout.newTab().setText("음악 차트"));
        tabLayout.addTab(tabLayout.newTab().setText("추천 리스트"));
        //tabLayout.addTab(tabLayout.newTab().setText("라인업"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) rootView.findViewById(R.id.pager_music);
        pagerAdapter = new MusicPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return rootView;
    }

}
