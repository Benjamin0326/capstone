package com.android.capstone.yolo.layer.community.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.BoardListAdapter;
import com.android.capstone.yolo.model.BoardList;
import com.android.capstone.yolo.scenario.scenario;

import java.util.List;

public class CommunityBoardActivity extends AppCompatActivity{
    ListView boardList;
    BoardListAdapter adapter;

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

            }
        });

        initView();
    }

    public void initView(){
        int boardID = getIntent().getExtras().getInt("communityID");

        List<BoardList> lists = scenario.getBoardList(boardID);
        adapter.setSource(lists);

        /*
        CommunityService service = network.buildRetrofit().create(CommunityService.class);
        Call<List<BoardList>> boardListCall = service.getBoardList(boardID);
        boardListCall.enqueue(new Callback<List<BoardList>>() {
            @Override
            public void onResponse(Call<List<BoardList>> call, Response<List<BoardList>> response) {
                if(response.isSuccessful()){
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
        */
    }
}
