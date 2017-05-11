package com.android.capstone.yolo.layer.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.BoardListAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.model.BoardList;
import com.android.capstone.yolo.service.CommunityService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityBoardActivity extends AppCompatActivity{
    ListView boardList;
    BoardListAdapter adapter;
    ImageView postBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_board);
        boardList = (ListView) findViewById(R.id.board_list);
        adapter = new BoardListAdapter(getApplicationContext());
        boardList.setAdapter(adapter);
        boardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BoardList boardList = (BoardList) adapter.getItem(i);
                Intent intent = new Intent(getApplicationContext(), BoardDetailActivity.class);
                intent.putExtra("postID", boardList.getId());
                startActivity(intent);
            }
        });

        postBtn = (ImageView) findViewById(R.id.post_btn);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewPostActivity.class);
                startActivity(intent);
            }
        });

        initView();
    }

    public void initView(){
        String boardID = getIntent().getExtras().getString("communityID");
        CommunityService service = network.buildRetrofit().create(CommunityService.class);
        Call<List<BoardList>> boardListCall = service.getBoardList(boardID);
        boardListCall.enqueue(new Callback<List<BoardList>>() {
            @Override
            public void onResponse(Call<List<BoardList>> call, Response<List<BoardList>> response) {
                if(response.isSuccessful()){
                    Log.d("TEST", response.body().toString());
                    adapter.setSource(response.body());
                    return;
                }
                int code = response.code();
                Log.d("TEST", "err code : " + code);
            }

            @Override
            public void onFailure(Call<List<BoardList>> call, Throwable t) {
                Log.d("TEST", "err : " + t.getMessage());
            }
        });
    }
}
