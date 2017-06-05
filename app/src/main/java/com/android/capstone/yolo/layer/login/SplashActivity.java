package com.android.capstone.yolo.layer.login;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.android.capstone.yolo.R;

import tyrantgit.explosionfield.ExplosionField;

public class SplashActivity extends AppCompatActivity{
    ImageView logo, background;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash);
        logo = (ImageView)findViewById(R.id.image_view_logo);
        background = (ImageView) findViewById(R.id.image_view_background);
        overridePendingTransition(R.anim.anim_transition_in, R.anim.anim_transition_out);
        final ExplosionField field = ExplosionField.attach2Window(this);
        Handler hd = new Handler();
        hd.postDelayed(new Runnable(){
            @Override
            public void run(){
                field.explode(background);
            }
        },1000);
        /*
        hd.postDelayed(new Runnable(){
            @Override
            public void run(){
                field.explode(logo);
            }
        },1500);
        */
        hd.postDelayed(new Runnable(){
            @Override
            public void run(){
                finish();
            }
        },2000);
    }
}
