package com.android.capstone.yolo.layer.festival;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.FestivalPagerAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.model.FestivalList;
import com.android.capstone.yolo.service.FestivalService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FestivalInfoFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FestivalPagerAdapter pagerAdapter;


    public FestivalInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_festival_info, container, false);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_festival_info);
        tabLayout.addTab(tabLayout.newTab().setText("정보"));
        tabLayout.addTab(tabLayout.newTab().setText("사진"));
        tabLayout.addTab(tabLayout.newTab().setText("영상"));
        //tabLayout.addTab(tabLayout.newTab().setText("라인업"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        int id = getArguments().getInt("position", 0);
        viewPager = (ViewPager) rootView.findViewById(R.id.pager_festival_info);
        pagerAdapter = new FestivalPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount(), id);
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
