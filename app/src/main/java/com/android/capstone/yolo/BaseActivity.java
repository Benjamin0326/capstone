package com.android.capstone.yolo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

public class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Typekit typekit = Typekit.getInstance();

        typekit.addNormal(Typekit.createFromAsset(this, "fonts/NotoSansCJKkr-Regular.otf"));
        typekit.addBold(Typekit.createFromAsset(this, "fonts/NotoSansCJKkr-Bold.otf"));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
