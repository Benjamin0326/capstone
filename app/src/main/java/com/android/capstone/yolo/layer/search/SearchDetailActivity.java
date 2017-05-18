package com.android.capstone.yolo.layer.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.model.Post;
import com.android.capstone.yolo.service.CommunityService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchDetailActivity extends AppCompatActivity{
    TextView title, type, writer, content, date;
    LinearLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        initView();
        getPost();
    }

    public void initView(){
        title = (TextView) findViewById(R.id.search_result_title);
        writer = (TextView) findViewById(R.id.search_result_writer);
        type = (TextView) findViewById(R.id.search_result_type);
        content = (TextView) findViewById(R.id.search_result_content);
        date = (TextView) findViewById(R.id.search_result_date);
        layout = (LinearLayout) findViewById(R.id.search_result_content_layout);
    }

    public void getPost(){
        String resultID = getIntent().getExtras().getString("postID");
        /*
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
        */
        CommunityService service = network.buildRetrofit().create(CommunityService.class);
        Call<Post> postCall = service.getBoardDetail(resultID);
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
}
