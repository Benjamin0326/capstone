package com.android.capstone.yolo.layer.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.capstone.yolo.BaseActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.ReplyAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.model.Post;
import com.android.capstone.yolo.scenario.scenario;
import com.android.capstone.yolo.service.CommunityService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardDetailActivity extends BaseActivity{
    TextView title, type, writer, content, date;
    RecyclerView replyList;
    ReplyAdapter replyAdapter;
    LinearLayout layout;
    FrameLayout replyButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);

        initView();
        getPost();
    }

    public void initView(){
        title = (TextView) findViewById(R.id.detail_title);
        writer = (TextView) findViewById(R.id.detail_writer);
        type = (TextView) findViewById(R.id.detail_type);
        content = (TextView) findViewById(R.id.detail_content);
        date = (TextView) findViewById(R.id.detail_date);
        layout = (LinearLayout) findViewById(R.id.detail_content_layout);
        replyList = (RecyclerView) findViewById(R.id.board_detail_reply);
        replyList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        replyList.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
        replyAdapter = new ReplyAdapter(getApplicationContext());
        replyList.setAdapter(replyAdapter);
        replyButton = (FrameLayout) findViewById(R.id.detail_reply_button);
        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewReplyActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getPost(){
        final String postID = getIntent().getExtras().getString("postID");

        CommunityService service = network.buildRetrofit().create(CommunityService.class);
        Call<Post> postCall = service.getBoardDetail(postID);
        postCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    title.setText(response.body().getTitle());
                    content.setText(response.body().getContent());
                    writer.setText(response.body().getUser());
                    date.setText(response.body().getDate());
                    type.setText("["+response.body().getTag()+"]");

                    if(response.body().getImg().length > 0){
                        for(int i = 0; i<response.body().getImg().length; i++){
                            ImageView imageView = new ImageView(getApplicationContext());
                            Picasso.with(getApplicationContext()).load(response.body().getImg()[i]).into(imageView);
                            layout.addView(imageView);
                        }
                        getReply(postID);
                    }
                    return;
                }
                Log.d("TEST", "err code : " + response.code());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d("TEST", "err msg : " + t.getMessage());
            }
        });
    }

    public void getReply(String id){
        /*
        CommunityService service = network.buildRetrofit().create(CommunityService.class);
        Call<List<Reply>> call = service.getReply(id);
        call.enqueue(new Callback<List<Reply>>() {
            @Override
            public void onResponse(Call<List<Reply>> call, Response<List<Reply>> response) {
                if(response.isSuccessful()) {
                    replyAdapter.setSource(response.body());
                    return;
                }
                Log.d("TEST", "err : " + response.code());
            }

            @Override
            public void onFailure(Call<List<Reply>> call, Throwable t) {
                Log.d("TEST", "err msg : " + t.getMessage().toString());
            }
        });
        */
        /*
        List<Reply> temp = scenario.getReply(id);
        for(int i=0; i<temp.size(); i++){
            Log.d("TEST", temp.get(i).getUser() + "");
            Log.d("TEST", temp.get(i).getDate() + "");
            Log.d("TEST", temp.get(i).getContent() + "");
            Log.d("TEST", temp.get(i).getPath() + "");
        }*/
        replyAdapter.setSource(scenario.getReply(id));
    }
}
