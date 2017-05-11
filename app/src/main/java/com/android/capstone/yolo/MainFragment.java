package com.android.capstone.yolo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.capstone.yolo.adapter.FestivalListAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.layer.festival.ClickableViewPager;
import com.android.capstone.yolo.layer.festival.FestivalInfoFragment;
import com.android.capstone.yolo.model.FestivalList;
import com.android.capstone.yolo.service.FestivalService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private int[] img_festival = {R.drawable.festival1, R.drawable.festival2, R.drawable.festival3};
    private FestivalListAdapter adapter;
    private ClickableViewPager pager;
    private List<FestivalList> festivalLists;

    public MainFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        pager = (ClickableViewPager) rootView.findViewById(R.id.pager_festival_list);
        //adapter = new FestivalListAdapter(getActivity().getSupportFragmentManager(), img_festival.length, img_festival, getActivity());
        adapter = new FestivalListAdapter(getChildFragmentManager(), getActivity(), festivalLists);
        pager.setAdapter(adapter);
        pager.setOnItemClickListener(new ClickableViewPager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Fragment fr = new FestivalInfoFragment();
                Bundle args = new Bundle();
                args.putInt("position", position);

                fr.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container_fragment, fr).commit();
            }
        });
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
                    adapter = new FestivalListAdapter(getChildFragmentManager(), getActivity(), festivalLists);
                    pager.setAdapter(adapter);
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





/*

package com.android.capstone.yolo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MainFragment extends Fragment {
    private int[] img_festival = {R.drawable.festival1, R.drawable.festival2, R.drawable.festival3};
    private FestivalAdapter adapter;
    private RecyclerView recycler;
    private LinearLayoutManager layoutManager;

    public MainFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        recycler = (RecyclerView) rootView.findViewById(R.id.recycler_festival_list);
        adapter = new FestivalAdapter(getActivity(), img_festival);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(layoutManager);
        return rootView;
    }

}

 */