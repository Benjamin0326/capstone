package com.android.capstone.yolo.layer.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.capstone.yolo.MainActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.ProfileMusicAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.layer.community.NewReplyActivity;
import com.android.capstone.yolo.model.Music;
import com.android.capstone.yolo.model.ProfileImage;
import com.android.capstone.yolo.model.ProfileImageByName;
import com.android.capstone.yolo.service.ProfileService;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {

    private TextView user_name;
    private RecyclerView recyclerView;
    private ProfileMusicAdapter adapter;
    private List<Music> music;
    String strId, strName, strImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user_name = (TextView) findViewById(R.id.user_profile_title);
        recyclerView = (RecyclerView) findViewById(R.id.user_profile_recycler);

        adapter = new ProfileMusicAdapter(this, music);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        strId = getIntent().getExtras().getString("userId");
        //user_name.setText();
        getUserProfile(strId);
    }

    public void getUserProfile(String id){

        ProfileService service = network.buildRetrofit().create(ProfileService.class);
        Call<ProfileImage> call = service.getProfile(id, MainActivity.token);
        call.enqueue(new Callback<ProfileImage>() {
            @Override
            public void onResponse(Call<ProfileImage> call, final Response<ProfileImage> response) {
                if(response.isSuccessful()) {
                    Log.d("Test : ", response.body().getName()+"\n"+response.body().getImage());
                    user_name.setText(response.body().getName());
                    //Picasso.with(getApplicationContext()).load(response.body().getImage()).into(user_profile);
                    /*
                    CircleImageView.OnClickListener img_listener = new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                            intent.putExtra("userId", response.body().get_id());
                            startActivity(intent);
                        }
                    };

                    user_profile.setOnClickListener(img_listener);
                    */
                    return;
                }

                if(response.code() >= 500) {
                    Toast.makeText(getApplicationContext(), "Profile Image Server err " + response.code() + " : " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileImage> call, Throwable t) {
                Log.d("TEST", "err msg : " + t.getMessage().toString());
            }
        });
    }

}

/*

        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
        intent.putExtra("userName", userName);
        startActivity(intent);

 */
