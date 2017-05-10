package com.android.capstone.yolo.layer.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.capstone.yolo.MainActivity;
import com.android.capstone.yolo.R;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login, btn_join;
    private EditText id, pw;
    private Intent intent;
    private String text_id, text_pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_join = (Button) findViewById(R.id.btn_join);
        id = (EditText) findViewById(R.id.edit_login_mail);
        pw = (EditText) findViewById(R.id.edit_login_pw);
        text_id = id.getText().toString();
        text_pw = pw.getText().toString();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                intent.putExtra(MainActivity.RETURN_RESULT, MainActivity.SUCCESS_LOGIN);
                setResult(RESULT_OK, intent);
                finish();
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
}