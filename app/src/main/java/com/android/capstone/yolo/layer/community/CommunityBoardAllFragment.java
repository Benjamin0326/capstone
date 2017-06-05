package com.android.capstone.yolo.layer.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.capstone.yolo.MainActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.BoardListAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.model.BoardList;
import com.android.capstone.yolo.service.CommunityService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityBoardAllFragment extends Fragment{
    ListView boardList;
    BoardListAdapter adapter;
    String communityID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_board_all, container, false);
        boardList = (ListView) view.findViewById(R.id.board_list);
        adapter = new BoardListAdapter(getContext());
        boardList.setAdapter(adapter);
        boardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(((CommunityBoardActivity)getActivity()).isFloatingMenuOpened())
                    ((CommunityBoardActivity)getActivity()).closeFloatingMenu();

                BoardList boardList = (BoardList) adapter.getItem(i);
                Intent intent = new Intent(getContext(), BoardDetailActivity.class);
                intent.putExtra("postID", boardList.getId());
                intent.putExtra("communityTitle", getActivity().getIntent().getExtras().getString("communityTitle"));
                startActivity(intent);
            }
        });

        communityID = getActivity().getIntent().getExtras().getString("communityID");

        getPostList(communityID);
        return view;
    }

    public void getPostList(String id){
        Log.d("TEST", "get post list " + id);
        CommunityService service = network.buildRetrofit().create(CommunityService.class);
        Call<List<BoardList>> boardListCall = service.getBoardList(id, MainActivity.token);
        boardListCall.enqueue(new Callback<List<BoardList>>() {
            @Override
            public void onResponse(Call<List<BoardList>> call, Response<List<BoardList>> response) {
                if(response.isSuccessful()){
                    adapter.setSource(response.body());
                    return;
                }

                if(response.code() >= 500) {
                    Toast.makeText(getContext(), "Server err " + response.code() + " : " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BoardList>> call, Throwable t) {
                Log.d("TEST", "err : " + t.getMessage());
            }
        });
    }
}
