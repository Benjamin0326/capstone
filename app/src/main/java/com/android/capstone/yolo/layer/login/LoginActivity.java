package com.android.capstone.yolo.layer.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.capstone.yolo.MainActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.model.Login;
import com.android.capstone.yolo.model.UserInfo;
import com.android.capstone.yolo.service.LoginService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login, btn_join;
    private EditText id, pw;
    private Intent intent;
    private String text_id, text_pw;
    private Boolean doubleBackToExitPressedOnce=false;
    private Login loginInfo;
    //private UserInfo userInfo = new UserInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_join = (Button) findViewById(R.id.btn_join);
        id = (EditText) findViewById(R.id.edit_login_mail);
        pw = (EditText) findViewById(R.id.edit_login_pw);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text_id = id.getText().toString();
                if(text_id.length()==0) {
                    Toast.makeText(LoginActivity.this, "ID를 다시 확인해주세요.", Toast.LENGTH_LONG).show();
                    return;
                }
                text_pw = pw.getText().toString();
                if(text_pw.length()==0){
                    Toast.makeText(LoginActivity.this, "Password를 다시 확인해주세요.", Toast.LENGTH_LONG).show();
                    return;
                }
                postLogin();
                /*
                intent = new Intent();
                intent.putExtra(MainActivity.RETURN_RESULT, MainActivity.SUCCESS_LOGIN);
                setResult(RESULT_OK, intent);
                finish();
                */
            }
        });

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }

    public void postLogin(){

        LoginService service = network.buildRetrofit().create(LoginService.class);
        Call<Login> loginCall = service.postLogin("", text_id, text_pw);

        loginCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.isSuccessful()){
                    loginInfo = response.body();

                    Log.d("Login Info : ", loginInfo.getUser_token());
                    //for(int i=0;i<festivalLists.get(position).getVideo().length;i++){
                    //    Log.d("#Test :", festivalLists.get(position).getVideo()[i]);
                    //}
                    //Picasso.with(getActivity()).load(festivalLists.get(position).getImg()[1]).into(img);

                    intent = new Intent();
                    intent.putExtra(MainActivity.RETURN_RESULT, MainActivity.SUCCESS_LOGIN);
                    setResult(RESULT_OK, intent);
                    SharedPreferences.Editor editor = MainActivity.pref.edit();
                    MainActivity.token = loginInfo.getUser_token();
                    editor.putString("token", loginInfo.getUser_token());
                    editor.putString("id", text_id);
                    editor.commit();
                    finish();
                    return;
                }
                int code = response.code();
                Log.d("TEST", "err code : " + code);
                Toast.makeText(LoginActivity.this, "err code : " + code, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Failed to Access", Toast.LENGTH_LONG).show();
                Log.i("TEST","err : "+ t.getMessage());
            }
        });
    }
}
