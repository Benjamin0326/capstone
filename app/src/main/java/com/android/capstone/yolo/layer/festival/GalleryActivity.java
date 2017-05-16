package com.android.capstone.yolo.layer.festival;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.capstone.yolo.R;
import com.squareup.picasso.Picasso;

public class GalleryActivity extends AppCompatActivity {

    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        img = (ImageView) findViewById(R.id.img_gallery);
        String adr = getIntent().getStringExtra("gallery");
        Picasso.with(this).load(adr).into(img);
    }
}
