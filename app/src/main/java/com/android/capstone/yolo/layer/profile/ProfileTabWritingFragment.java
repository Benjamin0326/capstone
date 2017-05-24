package com.android.capstone.yolo.layer.profile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.capstone.yolo.MainActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.MusicRankAdapter;
import com.android.capstone.yolo.adapter.ProfileWritingAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.model.Music;
import com.android.capstone.yolo.model.Post;
import com.android.capstone.yolo.service.MusicService;
import com.android.capstone.yolo.service.ProfileService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private List<Post> post;

    public ProfileTabWritingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_profile_tab_writing, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_profile_tab_writing);
        adapter = new ProfileWritingAdapter(getContext(), post);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        getMyBoard();
        return rootView;
    }

    public void getMyBoard(){
        ProfileService service = network.buildRetrofit().create(ProfileService.class);
        Call<List<Post>> myBoardListCall = service.getMyBoard(MainActivity.pref.getString("id",null));
        //Call<List<Post>> myBoardListCall = service.getMyBoard("김성철");

        myBoardListCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()){
                    post = response.body();
                    adapter = new ProfileWritingAdapter(getContext(), post);

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
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to load thumbnails", Toast.LENGTH_LONG).show();
                Log.i("TEST","err : "+ t.getMessage());
            }
        });
    }


}
