package com.android.capstone.yolo.layer.community.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.CommunityListAdapter;
import com.android.capstone.yolo.layer.community.main.CommunityBoardActivity;
import com.android.capstone.yolo.model.CommunityList;
import com.android.capstone.yolo.scenario.scenario;

import java.util.List;

public class CommunityListFrag extends Fragment{
    ListView listView;
    CommunityListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_community_list, container, false);
        listView = (ListView) rootView.findViewById(R.id.community_list);
        adapter = new CommunityListAdapter(getContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CommunityList list = (CommunityList) adapter.getItem(i);
                Intent intent = new Intent((getActivity()), CommunityBoardActivity.class);
                intent.putExtra("communityID", list.id);
                startActivity(intent);
            }
        });

        getCommunityList();

        return rootView;
    }

    public void getCommunityList(){
        List<CommunityList> lists = scenario.getCommunityList();
        adapter.setSource(lists);

        /*
        // apply after server
        FestivalService service = network.buildRetrofit().create(FestivalService.class);
        Call<List<CommunityList>> communityListCall = service.getCommunityList();

        communityListCall.enqueue(new Callback<List<CommunityList>>() {
            @Override
            public void onResponse(Call<List<CommunityList>> call, Response<List<CommunityList>> response) {
                if(response.isSuccessful()){
                    List<CommunityList> lists = response.body();
                    adapter.setSource(lists);
                    return;
                }
                int code = response.code();
                Log.d("TEST", "err code : " + code);
            }

            @Override
            public void onFailure(Call<List<CommunityList>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to load thumbnails", Toast.LENGTH_LONG).show();
                Log.i("TEST","err : "+ t.getMessage());
            }
        });
        */
    }
}
