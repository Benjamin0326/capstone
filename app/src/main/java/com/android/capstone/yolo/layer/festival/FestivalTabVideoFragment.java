package com.android.capstone.yolo.layer.festival;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.android.capstone.yolo.adapter.FestivalVideoAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.model.FestivalList;
import com.android.capstone.yolo.model.VideoAuthor;
import com.android.capstone.yolo.model.VideoInfo;
import com.android.capstone.yolo.service.FestivalService;
import com.android.capstone.yolo.service.VideoInfoService;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FestivalTabVideoFragment extends Fragment {
    String[] addr;
    Uri[] uri;
    List<String> author = new ArrayList<>();

    private int position;
    private FestivalVideoAdapter adapter;
    private RecyclerView recyclerView;
    private List<FestivalList> festivalLists;
    private List<VideoInfo> videoInfoList = new ArrayList<>();
    private VideoAuthor videoAuthor;
    VideoInfo videoInfo;

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
                    adapter = new FestivalVideoAdapter(getContext(), festivalLists.get(position).getVedio());
                    for(int i=0;i<festivalLists.get(position).getVedio().length;i++) {
                        Log.d("Video Resources:", festivalLists.get(position).getVedio()[i]);
                        getVideoAuthorInfo(i);
                    }
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

    public void getVideoAuthorInfo(final int pos){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://www.youtube.com");
        builder.addConverterFactory(GsonConverterFactory.create());
        VideoInfoService service = builder.build().create(VideoInfoService.class);
        Log.d("getAuthor Test : ", festivalLists.get(position).getVedio()[pos]);
        Call<VideoAuthor> videoAuthorCall = service.getAuthor("https://www.youtube.com/watch?v="+festivalLists.get(position).getVedio()[pos]+"&format=json");

        videoAuthorCall.enqueue(new Callback<VideoAuthor>() {
            @Override
            public void onResponse(Call<VideoAuthor> call, Response<VideoAuthor> response) {
                if(response.isSuccessful()){
                    videoAuthor = response.body();
                    Log.d("Response Test : ", videoAuthor.toString()+" "+videoAuthor.getAuthor_name());
                    try {
                        author.add(videoAuthor.getAuthor_name());
                        Log.d("Video Author:", videoAuthor.getAuthor_name());
                        getVideoInfo(pos);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


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
            public void onFailure(Call<VideoAuthor> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to get author name", Toast.LENGTH_LONG).show();
                Log.i("TEST","err : "+ t.getMessage());
            }
        });
    }

    public void getVideoInfo(int pos){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("https://www.googleapis.com");
        builder.addConverterFactory(GsonConverterFactory.create());
        VideoInfoService service = builder.build().create(VideoInfoService.class);
        Call<VideoInfo> videoInfoCall = service.getVideoInfo(festivalLists.get(position).getVedio()[pos], "AIzaSyA9ZLVAgLKnHP1281N9n_KtQhSRP-jTKe4", "snippet,statistics", "items(id,snippet,statistics)");

        videoInfoCall.enqueue(new Callback<VideoInfo>() {
            @Override
            public void onResponse(Call<VideoInfo> call, Response<VideoInfo> response) {
                if(response.isSuccessful()){
                    videoInfo = response.body();
                    //Log.d("Response Test : ", videoInfo.toString()+" "+ videoInfo.videos.get(0).snippet.getTitle() + " " + videoInfo.videos.get(0).statistics.getViewCount());
                    try {
                        videoInfoList.add(videoInfo);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


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
            public void onFailure(Call<VideoInfo> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to get author name", Toast.LENGTH_LONG).show();
                Log.i("TEST","err : "+ t.getMessage());
            }
        });
    }

}
