package com.android.capstone.yolo.layer.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.model.Login;
import com.android.capstone.yolo.service.LoginService;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {

    private CircleImageView cropImage;
    private Uri mCropImageUri, resultUri;
    private Button btn_join;
    private EditText id, pw, confirm_pw, name;
    private String strId, strPw, strConfirmPw, strName;
    private Login loginInfo = new Login();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        id = (EditText) findViewById(R.id.edit_join_mail);
        pw = (EditText) findViewById(R.id.edit_join_pw);
        confirm_pw = (EditText) findViewById(R.id.edit_join_confirm_pw);
        name = (EditText) findViewById(R.id.edit_join_name);


        cropImage = (CircleImageView) findViewById(R.id.image_crop);
        cropImage.setBorderColor(Color.WHITE);
        cropImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImageClick(cropImage);
            }
        });
        btn_join = (Button) findViewById(R.id.btn_join_ok);
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strId=id.getText().toString();
                if(strId.length()==0) {
                    Toast.makeText(JoinActivity.this, "ID를 다시 확인해주세요.", Toast.LENGTH_LONG).show();
                    return;
                }
                strName=name.getText().toString();
                if(strName.length()==0) {
                    Toast.makeText(JoinActivity.this, "Name을 다시 확인해주세요.", Toast.LENGTH_LONG).show();
                    return;
                }
                strPw=pw.getText().toString();
                if(strPw.length()==0) {
                    Toast.makeText(JoinActivity.this, "Password를 다시 확인해주세요.", Toast.LENGTH_LONG).show();
                    return;
                }

                strConfirmPw=confirm_pw.getText().toString();
                if(strConfirmPw.length()==0) {
                    Toast.makeText(JoinActivity.this, "Confirom Password를 다시 확인해주세요.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(strPw.compareTo(strConfirmPw)!=0){
                    Toast.makeText(JoinActivity.this, "Password가 서로 다릅니다.", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    if(resultUri!=null)
                        saveUriPreferences(resultUri);
                    saveIDPreferences(id.getText().toString());
                    postJoin();
                    //finish();
                }
            }
        });

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CropImage.startPickImageActivity(this);
            } else {
                Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // required permissions granted, start crop image activity
                startCropImageActivity(mCropImageUri);
            } else {
                Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                Picasso.with(this).load(resultUri).into(cropImage);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    private void startCropImageActivity(Uri uri) {
        CropImage.activity(uri).setCropShape(CropImageView.CropShape.OVAL).start(this);
    }
    public void onSelectImageClick(View view) {
        CropImage.startPickImageActivity(this);
    }
    private void saveUriPreferences(Uri _uri){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("profile", _uri.toString());
        editor.commit();
    }

    private void saveIDPreferences(String _id){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("id", _id);
        editor.commit();
    }

    public void postJoin(){
        LoginService service = network.buildRetrofit().create(LoginService.class);
        Call<Login> joinCall = service.postJoin(strName, strId, strPw);

        joinCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.isSuccessful()){
                    loginInfo = response.body();

                    Log.d("Join Info : ", loginInfo.getMessage()+"\n"+loginInfo.getStatus_code()+"\n"+loginInfo.getUser_token());
                    //for(int i=0;i<festivalLists.get(position).getVideo().length;i++){
                    //    Log.d("#Test :", festivalLists.get(position).getVideo()[i]);
                    //}
                    //Picasso.with(getActivity()).load(festivalLists.get(position).getImg()[1]).into(img);
                    finish();
                    return;
                }
                int code = response.code();
                Log.d("TEST", "err code : " + code);
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(JoinActivity.this, "Failed to Access", Toast.LENGTH_LONG).show();
                Log.i("TEST","err : "+ t.getMessage());
            }
        });
    }

}
