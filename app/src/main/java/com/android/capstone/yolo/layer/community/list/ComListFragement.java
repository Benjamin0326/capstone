package com.android.capstone.yolo.layer.community.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.ComListAdapter;
import com.android.capstone.yolo.model.CommunityList;
import com.android.capstone.yolo.scenario.scenario;

import java.util.List;

public class ComListFragement extends Fragment{
    ListView listView;
    ComListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_community_list, container, false);
        listView = (ListView) rootView.findViewById(R.id.community_list);
        adapter = new ComListAdapter(getContext());
        listView.setAdapter(adapter);

        getCommunityList();

        return rootView;
    }

    public void getCommunityList(){
        List<CommunityList> lists = scenario.getCommunityList();
        adapter.setSource(lists);
        /*
        FestivalService listService = network.buildRetrofit().create(FestivalService.class);
        Call<List<CommunityList>> call = scenario.getCommunityList();

        call.enqueue(new Callback<List<CommunityList>>() {
            @Override
            public void onResponse(Call<List<CommunityList>> call, Response<List<CommunityList>> response) {
                List<CommunityList> lists = response.body();
                adapter.setSource(lists);
            }

            @Override
            public void onFailure(Call<List<CommunityList>> call, Throwable t) {
                Log.d("TEST", "" + t.getMessage());
            }
        });
        */
    }
}
