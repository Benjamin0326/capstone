package com.android.capstone.yolo.layer.festival;


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

import com.android.capstone.yolo.MainActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.FestivalPictureAdapter;
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
public class FestivalTabPictureFragment extends Fragment {
    String[] addr = {"http://www.flooringvillage.co.uk/ekmps/shops/flooringvillage/images/request-a-sample--547-p.jpg",
    "http://inspectiondoc.com/wp-content/uploads/2014/08/sample-icon.png",
    "https://thumb9.shutterstock.com/display_pic_with_logo/436114/274833056/stock-vector-sample-grunge-retro-red-isolated-ribbon-stamp-sample-stamp-sample-sample-sign-274833056.jpg"};

    Uri[] uri;

    private FestivalPictureAdapter adapter;
    private RecyclerView recyclerView;
    private List<FestivalList> festivalLists;
    private int position;
    public FestivalTabPictureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        uri = new Uri[addr.length];
        for(int i=0;i<addr.length;i++){
            uri[i] = Uri.parse(addr[i]);
        }
        View rootView =  inflater.inflate(R.layout.fragment_festival_tab_picture, container, false);
        position = getArguments().getInt("position", 999);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_festival_tab_picture);
        adapter = new FestivalPictureAdapter(getContext(), addr);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        getFestivalInfoList();
        return rootView;
    }

    public void getFestivalInfoList(){
        FestivalService service = network.buildRetrofit().create(FestivalService.class);
        Call<List<FestivalList>> festivalListCall = service.getFestival(MainActivity.token);

        festivalListCall.enqueue(new Callback<List<FestivalList>>() {
            @Override
            public void onResponse(Call<List<FestivalList>> call, Response<List<FestivalList>> response) {
                if(response.isSuccessful()){
                    festivalLists = response.body();
                    adapter = new FestivalPictureAdapter(getContext(), festivalLists.get(position).getImg());
                    for(int i=0;i<festivalLists.get(position).getImg().length;i++)
                        Log.d("#Test Image Resources", festivalLists.get(position).getImg()[i]);
                    recyclerView.setAdapter(adapter);
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
