package com.android.capstone.yolo.layer.profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.ProfileWritingAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTabWritingFragment extends Fragment {

    private ProfileWritingAdapter adapter;
    private RecyclerView recyclerView;
    private String[] categories = {"A Festival", "B Festival", "C Festival", "D Festival", "E Festival"};
    private String[] subject = {"투명하되 뼈 얼마나 생명을 사람은 것이다.", "황금시대의 그들의 피어나는 그리하였는가?"
    ,"인류의 방지하는 설레는", "자신과 천자만홍이 위하여", "때까지 그들은 같이,"};
    private String[] date = {"2017. 05. 04", "2017. 05. 04", "2017. 05. 04", "2017. 05. 04", "2017. 05. 04" };

    public ProfileTabWritingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_profile_tab_writing, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_profile_tab_writing);
        adapter = new ProfileWritingAdapter(getContext(), subject, categories, date);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        return rootView;
    }

}
