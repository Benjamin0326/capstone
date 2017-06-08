package com.android.capstone.yolo.layer.community;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.capstone.yolo.MainActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.BoardListAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.model.BoardList;
import com.android.capstone.yolo.model.Post;
import com.android.capstone.yolo.service.CommunityService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityBoardPopularFragment extends Fragment{
    ListView boardList;
    BoardListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_board_popular, container, false);
        boardList = (ListView) view.findViewById(R.id.board_list);
        adapter = new BoardListAdapter(getContext());
        boardList.setAdapter(adapter);

        getHotList();

        return view;
    }

    public void getHotList(){
        Log.d("TEST", "id : " + ((CommunityBoardActivity)getActivity()).getCommunityID());
        CommunityService service = network.buildRetrofit().create(CommunityService.class);
        Call<List<BoardList>> call = service.getHotList(((CommunityBoardActivity)getActivity()).getCommunityID(), MainActivity.token);
        call.enqueue(new Callback<List<BoardList>>() {
            @Override
            public void onResponse(Call<List<BoardList>> call, Response<List<BoardList>> response) {
                if(response.isSuccessful()){
                    adapter.setSource(response.body());
                    return;
                }
                Toast.makeText(getContext(), "err " + response.code() + " : " + response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<BoardList>> call, Throwable t) {
                Log.d("TEST", "err " + t.getMessage());
            }
        });
    }
}
