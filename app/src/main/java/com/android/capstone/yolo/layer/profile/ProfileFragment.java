package com.android.capstone.yolo.layer.profile;


import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.FestivalPagerAdapter;
import com.android.capstone.yolo.adapter.ProfilePagerAdapter;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ProfilePagerAdapter pagerAdapter;
    private CircleImageView thumbnail;
    private TextView name, info;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        thumbnail = (CircleImageView) rootView.findViewById(R.id.thumbnail_profile);
        name = (TextView) rootView.findViewById(R.id.text_profile_name);
        info = (TextView) rootView.findViewById(R.id.text_profile_info);

        String id = getIdPreferences();
        if(id!=null)
            name.setText(id);
        Uri imageUri = getUriPreferences();
        if(imageUri!=null)
            Picasso.with(getActivity()).load(imageUri).into(thumbnail);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_profile);
        tabLayout.addTab(tabLayout.newTab().setText("음악 리스트"));
        tabLayout.addTab(tabLayout.newTab().setText("작성한 글"));
        tabLayout.addTab(tabLayout.newTab().setText("작성한 댓글"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) rootView.findViewById(R.id.pager_profile);
        pagerAdapter = new ProfilePagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
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


    private Uri getUriPreferences(){
        SharedPreferences pref = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
        String tmp = pref.getString("profile", "");
        if(tmp.compareTo("")==0){
            return null;
        }
        else{
            Uri uri = Uri.parse(tmp);
            return uri;
        }
    }

    private String getIdPreferences(){
        SharedPreferences pref = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
        String tmp = pref.getString("id", "");
        if(tmp.compareTo("")==0){
            return null;
        }
        else{
            return tmp;
        }
    }


}
