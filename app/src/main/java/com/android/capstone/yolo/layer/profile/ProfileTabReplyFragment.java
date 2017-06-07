package com.android.capstone.yolo.layer.profile;


import android.content.Intent;
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
import com.android.capstone.yolo.adapter.ProfileReplyAdapter;
import com.android.capstone.yolo.adapter.ProfileWritingAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.model.Post;
import com.android.capstone.yolo.model.ProfileImageByName;
import com.android.capstone.yolo.model.Reply;
import com.android.capstone.yolo.service.ProfileService;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTabReplyFragment extends Fragment {

    private ProfileReplyAdapter adapter;
    private RecyclerView recyclerView;
    //private String[] categories = {"A Festival", "B Festival", "C Festival", "D Festival", "E Festival"};
    /*private String[] reply = {"투명하되 뼈 얼마나 생명을 사람은 것이다.", "황금시대의 그들의 피어나는 그리하였는가?"
            ,"인류의 방지하는 설레는", "자신과 천자만홍이 위하여", "때까지 그들은 같이,", "우는 놀이 우리 봄바람이다.",
    "커다란 그들에게 천고에 맺어, 힘있다. ", "내는 것은 방황하여도, 우는 물방아 작고 불러 피가 듣는다.",
    "더운지라 무엇이 없으면, 천지는 바로 청춘의 무엇을 위하여서.", "전인 이상은 이상, 가슴이 것은 무엇을 심장은 온갖 약동하다."};
    */
    private String[] date;
    private List<Reply> reply;
    private String userId;

    public ProfileTabReplyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile_tab_reply, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_profile_tab_reply);
        adapter = new ProfileReplyAdapter(getContext(), reply);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        getUserProfile();

        return rootView;
    }

    public void getUserProfile(){

        ProfileService service = network.buildRetrofit().create(ProfileService.class);
        Call<ProfileImageByName> call = service.getUserImageByName(ProfileFragment.userName, MainActivity.token);
        call.enqueue(new Callback<ProfileImageByName>() {
            @Override
            public void onResponse(Call<ProfileImageByName> call, final Response<ProfileImageByName> response) {
                if(response.isSuccessful()) {
                    Log.d("Test : ", response.body().get_id()+"\n"+response.body().getImage());
                    userId = response.body().get_id();
                    getMyComment(userId);
                    return;
                }

                if(response.code() >= 500) {
                    Toast.makeText(getContext(), "Profile Server err " + response.code() + " : " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileImageByName> call, Throwable t) {
                Log.d("TEST", "err msg : " + t.getMessage().toString());
            }
        });
    }

    public void getMyComment(String id){
        ProfileService service = network.buildRetrofit().create(ProfileService.class);
        Call<List<Reply>> myCommentListCall = service.getMyComment(id, MainActivity.token);
        //Call<List<Reply>> myCommentListCall = service.getMyComment("김효종");

        myCommentListCall.enqueue(new Callback<List<Reply>>() {
            @Override
            public void onResponse(Call<List<Reply>> call, Response<List<Reply>> response) {
                if(response.isSuccessful()){
                    reply = response.body();
                    adapter = new ProfileReplyAdapter(getContext(), reply);

                    recyclerView.setAdapter(adapter);
                    Log.d("Test : ", reply.get(0).get_id()+"\n"+reply.get(0).getPath());
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
            public void onFailure(Call<List<Reply>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to load thumbnails", Toast.LENGTH_LONG).show();
                Log.i("TEST","err : "+ t.getMessage());
            }
        });
    }
}
