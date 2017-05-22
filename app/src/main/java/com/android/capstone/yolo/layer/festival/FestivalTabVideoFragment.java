package com.android.capstone.yolo.layer.festival;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.FestivalPictureAdapter;
import com.android.capstone.yolo.adapter.FestivalVideoAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.model.FestivalList;
import com.android.capstone.yolo.service.FestivalService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FestivalTabVideoFragment extends Fragment {
    String[] addr = {"iQpGq4HguVs",
            "pFscrs6qmDY",
            "PfQhLcYqE88", "4r1tq1XBN0w"};
    Uri[] uri;

    private int position;
    private FestivalVideoAdapter adapter;
    private RecyclerView recyclerView;
    private List<FestivalList> festivalLists;

    public FestivalTabVideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_festival_tab_video, container, false);
        /*uri = new Uri[addr.length];
        for(int i=0;i<addr.length;i++){
            uri[i] = Uri.parse(addr[i]);
        }
        */
        position = getArguments().getInt("position", 0);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_festival_tab_video);
        adapter = new FestivalVideoAdapter(getContext(), addr);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        getFestivalInfoList();
        return rootView;
    }

    public void getFestivalInfoList(){
        FestivalService service = network.buildRetrofit().create(FestivalService.class);
        Call<List<FestivalList>> festivalListCall = service.getFestival();

        festivalListCall.enqueue(new Callback<List<FestivalList>>() {
            @Override
            public void onResponse(Call<List<FestivalList>> call, Response<List<FestivalList>> response) {
                if(response.isSuccessful()){
                    festivalLists = response.body();
                    adapter = new FestivalVideoAdapter(getContext(), festivalLists.get(position).getVedio());
                    for(int i=0;i<festivalLists.get(position).getVedio().length;i++)
                        Log.d("Video Resources:", festivalLists.get(position).getVedio()[i]);
                    recyclerView.setAdapter(adapter);
                    //for(int i=0;i<festivalLists.get(position).getVideo().length;i++){
                    //    Log.d("#Test :", festivalLists.get(position).getVideo()[i]);
                    //}
                    //Picasso.with(getActivity()).load(festivalLists.get(position).getImg()[1]).into(img);
                    return;
                }
                int code = response.code();
                Log.d("TEST", "err code : " + code);
            }

            @Override
            public void onFailure(Call<List<FestivalList>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to load thumbnails", Toast.LENGTH_LONG).show();
                Log.i("TEST","err : "+ t.getMessage());
            }
        });

    }
}
