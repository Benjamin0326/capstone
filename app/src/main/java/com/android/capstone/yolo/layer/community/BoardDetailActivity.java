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
import android.widget.Toast;

import com.android.capstone.yolo.BaseActivity;
import com.android.capstone.yolo.MainActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.ReplyAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.layer.profile.UserProfileActivity;
import com.android.capstone.yolo.model.Post;
import com.android.capstone.yolo.model.ProfileImageByName;
import com.android.capstone.yolo.model.Reply;
import com.android.capstone.yolo.service.CommunityService;
import com.android.capstone.yolo.service.ProfileService;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardDetailActivity extends BaseActivity{
    final int REPLY_FLAG = 2;
    final int REPLY_CANCEL = 3;
    TextView title, type, writer, content, date, boardTitle;
    CircleImageView user_profile;
    RecyclerView replyList;
    ReplyAdapter replyAdapter;
    LinearLayout layout;
    FrameLayout replyButton, replyLayout;
    String postID;
    String[] images;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);

        initView();
    }

    public void initView(){
        user_profile = (CircleImageView) findViewById(R.id.board_detail_profile);
        boardTitle = (TextView) findViewById(R.id.board_detail_title);
        boardTitle.setText(getIntent().getExtras().getString("communityTitle"));
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
                intent.putExtra("replyCnt", replyAdapter.getItemCount());
                intent.putExtra("boardID", postID);
                startActivityForResult(intent, REPLY_FLAG);
            }
        });
        replyLayout = (FrameLayout) findViewById(R.id.board_no_reply);
        getPost();
    }

    public void getPost(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                postID = getIntent().getExtras().getString("postID");

                CommunityService service = network.buildRetrofit().create(CommunityService.class);
                Call<Post> postCall = service.getBoardDetail(postID, MainActivity.token);
                postCall.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, final Response<Post> response) {
                        if(response.isSuccessful()){
                            title.setText(response.body().getTitle());
                            content.setText(response.body().getContent());
                            writer.setText(response.body().getUser());
                            date.setText(response.body().getDate());
                            type.setText("["+response.body().getTag()+"]");
                            images = response.body().getImg();

                            if(images!=null) {
                                if (images.length > 0) {
                                    for (int i = 0; i < images.length; i++) {
                                        ImageView imageView = new ImageView(getApplicationContext());
                                        Picasso.with(getApplicationContext()).load(images[i]).into(imageView);
                                        layout.addView(imageView);
                                    }
                                }
                            }
                            getUserProfile(response.body().getUser());
                            getReply(postID);
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
        }).start();
    }
/*
    public void getPostInBackground(){(new AsyncTask<Void, Void, Response<Post>>(){

        @Override
        protected Response<Post> doInBackground(Void... voids) {
            return tempPost();
        }

        @Override
        protected void onPostExecute(Response<Post> postResponse) {
            set
        }
    }).execute();}
*/
    public void getUserProfile(String id){

        ProfileService service = network.buildRetrofit().create(ProfileService.class);
        Call<ProfileImageByName> call = service.getUserImageByName(id, MainActivity.token);
        call.enqueue(new Callback<ProfileImageByName>() {
            @Override
            public void onResponse(Call<ProfileImageByName> call, final Response<ProfileImageByName> response) {
                if(response.isSuccessful()) {
                    if(!response.body().getImage().equals("profile/profile.jpg"))
                        Picasso.with(getApplicationContext()).load(response.body().getImage()).into(user_profile);

                    CircleImageView.OnClickListener img_listener = new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                            intent.putExtra("userId", response.body().get_id());
                            startActivity(intent);
                        }
                    };

                    user_profile.setOnClickListener(img_listener);
                    return;
                }

                Toast.makeText(getApplicationContext(), "err " + response.code() + " : " + response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ProfileImageByName> call, Throwable t) {
                Log.d("TEST", "err msg : " + t.getMessage().toString());
            }
        });
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
                        replyLayout.setVisibility(View.GONE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REPLY_FLAG){
            if(resultCode == REPLY_FLAG) {
                getReply(postID);
                return;
            }
            if(resultCode == REPLY_CANCEL)
                return;
        }
        Toast.makeText(getApplicationContext(), "댓글을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
    }
}
