package com.android.capstone.yolo.layer.community;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.model.Post;
import com.android.capstone.yolo.scenario.scenario;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class BoardDetailActivity extends AppCompatActivity{
    TextView title, type, writer, content, date;
    private SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    LinearLayout layout;

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
    }

    public void getPost(){
        long postID = getIntent().getExtras().getLong("postID");
        Post post = scenario.getBoardDetail(postID);

        title.setText(post.getTitle());
        writer.setText(post.getWriter());
        type.setText("["+post.getType()+"]");
        date.setText(FORMAT.format(post.getDate()));
        content.setText(post.getContent());

        if(post.getImage().size() > 0){
            for(int i = 0; i<post.getImage().size(); i++){
                ImageView imageView = new ImageView(getApplicationContext());
                Picasso.with(getApplicationContext()).load(post.getImage().get(i)).into(imageView);
                layout.addView(imageView);
            }
        }
        /*
        CommunityService service = network.buildRetrofit().create(CommunityService.class);
        Call<Post> postCall = service.getBoardDetail(postID);
        postCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(response.isSuccessful()){
                    title.setText(response.body().getTitle());
                    content.setText(response.body().getContent());
                    writer.setText(response.body().getWriter());
                    date.setText(FORMAT.format(post.getDate()));
                    type.setText("["+response.body().getType()+"]");

                    if(response.body().getImage().size() > 0){
                        for(int i = 0; i<response.body().getImage().size(); i++){
                            ImageView imageView = new ImageView(getApplicationContext());
                            Picasso.with(getApplicationContext()).load(response.body().getImage().get(i)).into(imageView);
                            layout.addView(imageView);
                        }
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
        */
    }
}
