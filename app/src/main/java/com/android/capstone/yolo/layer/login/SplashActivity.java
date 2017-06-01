package com.android.capstone.yolo.layer.login;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.capstone.yolo.R;

public class SplashActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash);

        overridePendingTransition(R.anim.anim_transition_in, R.anim.anim_transition_out);

        Handler hd = new Handler();
        hd.postDelayed(new Runnable(){
            @Override
            public void run(){
                finish();
            }
        },1000);
    }
}
