package com.android.capstone.yolo.layer.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.capstone.yolo.BaseActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.BoardListAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.layer.search.SearchActivity;
import com.android.capstone.yolo.model.BoardList;
import com.android.capstone.yolo.service.CommunityService;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommunityBoardActivity extends BaseActivity{
    TextView title;
    ListView boardList;
    BoardListAdapter adapter;
    //ImageView postBtn;
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton postBtn, searchBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_board);
        boardList = (ListView) findViewById(R.id.board_list);
        title = (TextView) findViewById(R.id.com_board_title);
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
/*
        postBtn = (ImageView) findViewById(R.id.post_btn);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewPostActivity.class);
                startActivity(intent);
            }
        });
        */
        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.fab_menu);
        postBtn = (FloatingActionButton) findViewById(R.id.menu_write_post);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (floatingActionMenu.isOpened())
                    floatingActionMenu.close(true);

                Intent intent = new Intent(getApplicationContext(), NewPostActivity.class);
                startActivity(intent);
            }
        });
        searchBtn = (FloatingActionButton) findViewById(R.id.menu_search);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (floatingActionMenu.isOpened())
                    floatingActionMenu.close(true);

                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        initView();
    }

    public void initView(){
        String boardID = getIntent().getExtras().getString("communityID");
        title.setText(getIntent().getExtras().getString("communityTitle"));
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
    }
}
