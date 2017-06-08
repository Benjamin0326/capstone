package com.android.capstone.yolo.layer.community;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.capstone.yolo.BaseActivity;
import com.android.capstone.yolo.MainActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.ReplyAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.model.Reply;
import com.android.capstone.yolo.service.CommunityService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewReplyActivity extends BaseActivity{
    final int REPLY_FLAG = 2;
    final int REPLY_CANCEL = 3;
    ReplyAdapter replyAdapter;
    ImageView backBtn;
    RecyclerView replyList;
    TextView postBtn, replyCount, noReply;
    EditText replyText;
    String postID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reply);

        initView();
    }

    public void initView(){
        backBtn = (ImageView) findViewById(R.id.reply_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        postBtn = (TextView) findViewById(R.id.reply_post_btn);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(replyText.getText().length() == 0){
                    Toast.makeText(getApplicationContext(), "댓글을 입력해주세요" , Toast.LENGTH_SHORT).show();
                }else{
                    newReply();
                }
            }
        });
        replyText = (EditText) findViewById(R.id.new_reply);
        replyCount = (TextView) findViewById(R.id.reply_cnt);
        replyCount.setText("댓글 " + getIntent().getExtras().getInt("replyCnt") + " 개");
        noReply = (TextView) findViewById(R.id.no_reply);

        replyList = (RecyclerView) findViewById(R.id.new_reply_list);
        replyList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        replyList.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
        replyAdapter = new ReplyAdapter(getApplicationContext());
        replyList.setAdapter(replyAdapter);

        postID = getIntent().getExtras().getString("boardID");
        getReply(postID);
    }

    public void getReply(String id){

        CommunityService service = network.buildRetrofit().create(CommunityService.class);
        Call<List<Reply>> call = service.getReply(id, MainActivity.token);
        call.enqueue(new Callback<List<Reply>>() {
            @Override
            public void onResponse(Call<List<Reply>> call, Response<List<Reply>> response) {
                if(response.isSuccessful()) {
                    replyAdapter.setSource(response.body());
                    if(replyAdapter.getItemCount() > 0){
                        noReply.setVisibility(View.GONE);
                        replyList.setVisibility(View.VISIBLE);
                    }
                    return;
                }

                Toast.makeText(getApplicationContext(), "err " + response.code() + " : " + response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Reply>> call, Throwable t) {
                Log.d("TEST", "err msg : " + t.getMessage().toString());
            }
        });
    }

    public void newReply(){
        CommunityService service = network.buildRetrofit().create(CommunityService.class);
        Call<Void> call = service.postReply(postID, replyText.getText().toString(), MainActivity.token);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "댓글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                    setResult(REPLY_FLAG);
                    finish();
                    return;
                }

                Toast.makeText(getApplicationContext(), "err " + response.code() + " : " + response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("TEST", "err : " + t.getMessage().toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(REPLY_CANCEL);
        super.onBackPressed();
    }
}
