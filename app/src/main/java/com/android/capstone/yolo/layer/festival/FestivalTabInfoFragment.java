package com.android.capstone.yolo.layer.festival;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.capstone.yolo.MainActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.FestivalListAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.model.FestivalList;
import com.android.capstone.yolo.service.FestivalService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FestivalTabInfoFragment extends Fragment {

    private ImageView img;
    private TextView text;
    private List<FestivalList> festivalLists;
    private int position;

    public FestivalTabInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_festival_tab_info, container, false);

        position = getArguments().getInt("position", 999);
        img = (ImageView) rootView.findViewById(R.id.img_festival_info);
        text = (TextView) rootView.findViewById(R.id.text_festival_info);

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
                    Picasso.with(getActivity()).load(festivalLists.get(position).getImg()[1]).into(img);
                    text.setText(festivalLists.get(position).getContent());
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
