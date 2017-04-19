package com.android.capstone.yolo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.android.capstone.yolo.adapter.ComListAdapter;
import com.android.capstone.yolo.model.CommunityList;
import com.android.capstone.yolo.scenario.scenario;

import java.util.List;

public class CommunityListAcitivty extends AppCompatActivity{
    ListView listView;
    ComListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_list);

        initView();
    }

    public void initView(){
        listView = (ListView) findViewById(R.id.community_list);
        adapter = new ComListAdapter(getApplicationContext());
        listView.setAdapter(adapter);
        getCommunityList();
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
