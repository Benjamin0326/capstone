package com.android.capstone.yolo.layer.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.capstone.yolo.MainActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.model.Login;
import com.android.capstone.yolo.model.ProfileImage;
import com.android.capstone.yolo.service.LoginService;
import com.android.capstone.yolo.service.ProfileService;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

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
                    saveIDPreferences(id.getText().toString());
                    postJoin();

                    if(resultUri!=null) {
                        saveUriPreferences(resultUri);
                        postProfileImage();
                    }
                    finish();
                }
            }
        });

    }

    public void postProfileImage(){
        ProfileService service = network.buildRetrofit().create(ProfileService.class);
        Map<String, RequestBody> map = new HashMap<>();
        File file = new File(resultUri.getPath());
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        map.put("UploadFile\"; filename=\"photo.jpg\"", fileBody);
        Call<ProfileImage> profileCall = service.postUserImage(map, loginInfo.getUser_token());
        profileCall.enqueue(new Callback<ProfileImage>() {
            @Override
            public void onResponse(Call<ProfileImage> call, Response<ProfileImage> response) {
                if(response.isSuccessful()){
                    ProfileImage tmp = response.body();

                    Log.d("Join Info : ", tmp.getImage()+"\n"+loginInfo.getUser_token());
                    //for(int i=0;i<festivalLists.get(position).getVideo().length;i++){
                    //    Log.d("#Test :", festivalLists.get(position).getVideo()[i]);
                    //}
                    //Picasso.with(getActivity()).load(festivalLists.get(position).getImg()[1]).into(img);
                    return;
                }
                int code = response.code();
                Log.d("TEST", "err code : " + code);
            }

            @Override
            public void onFailure(Call<ProfileImage> call, Throwable t) {
                Toast.makeText(JoinActivity.this, "Failed to Save Profile Image", Toast.LENGTH_LONG).show();
                Log.i("TEST","err : "+ t.getMessage());
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
        SharedPreferences pref = getSharedPreferences(MainActivity.PREF, MODE_PRIVATE);
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

    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

}
