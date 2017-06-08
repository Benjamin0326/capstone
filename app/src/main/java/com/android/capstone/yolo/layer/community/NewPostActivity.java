package com.android.capstone.yolo.layer.community;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.capstone.yolo.BaseActivity;
import com.android.capstone.yolo.MainActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.model.BoardImage;
import com.android.capstone.yolo.model.BoardList;
import com.android.capstone.yolo.service.CommunityService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPostActivity extends BaseActivity {
    final int RESULT_GALLERY = 1;
    final int POST_FLAG = 2;
    final int POST_CANCEL = 3;
    ImageView backBtn, uploadImageBtn;
    Map<String, RequestBody> photo;
    FrameLayout postBtn, categorySpinner;
    LinearLayout imageListLayout;
    EditText title, content;
    Uri uri;
    Dialog categoryDialog;
    TextView categoryText;
    int idx = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        initView();

    }

    public void initView(){
        backBtn = (ImageView) findViewById(R.id.newPostBack);
        uploadImageBtn = (ImageView) findViewById(R.id.newPostImage);
        postBtn = (FrameLayout) findViewById(R.id.newPostBtn);
        title = (EditText) findViewById(R.id.newPostTitle);
        content = (EditText) findViewById(R.id.newPostContent);
        imageListLayout = (LinearLayout) findViewById(R.id.newPostImageList);
        photo = new HashMap<>();
        categorySpinner = (FrameLayout) findViewById(R.id.newPostCategory);
        categoryText = (TextView) findViewById(R.id.category_text);

        categorySpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryDialog = createDialog();
                categoryDialog.show();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewPostActivity.super.onBackPressed();
            }
        });

        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "need permission to access external storage", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(getParent(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else {
                    getImageFromGallery();
                }
            }
        });

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputCheck()) {
                    CommunityService service = network.buildRetrofit().create(CommunityService.class);
                    Call<BoardList> call = service.postText(getIntent().getExtras().getString("communityID"), categoryText.getText().toString(), title.getText().toString(), content.getText().toString(), MainActivity.token);
                    call.enqueue(new Callback<BoardList>() {
                        @Override
                        public void onResponse(Call<BoardList> call, Response<BoardList> response) {
                            if (response.isSuccessful()) {
                                if(photo.size() == 0) {
                                    setResult(POST_FLAG);
                                    finish();
                                    return;
                                }

                                if(response.body().getId()!="") {
                                    postImage(response.body().getId());
                                    return;
                                }
                                return;
                            }

                            Toast.makeText(getApplicationContext(), "err " + response.code() + " : " + response.message(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<BoardList> call, Throwable t) {
                            Log.d("TEST", "err : " + t.getMessage().toString());
                        }
                    });
                }
            }
        });
    }

    public void postImage(String id){
        CommunityService service = network.buildRetrofit().create(CommunityService.class);
        Log.d("TEST", "photo size : " + photo.size());
        Call<BoardImage> call = service.postImage(id, photo, MainActivity.token);
        call.enqueue(new Callback<BoardImage>() {
            @Override
            public void onResponse(Call<BoardImage> call, Response<BoardImage> response) {
                if(response.isSuccessful()){
                    setResult(POST_FLAG);
                    finish();
                }

                Toast.makeText(getApplicationContext(), "err " + response.code() + " : " + response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<BoardImage> call, Throwable t) {
                Log.d("TEST", "err : " + t.getMessage().toString());
            }
        });
    }

    public boolean inputCheck(){
        if(categoryText.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(title.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(content.getText().toString().length() == 0){
            Toast.makeText(getApplicationContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private AlertDialog createDialog() {
        final String[] str = getResources().getStringArray(R.array.cagetory);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setSingleChoiceItems(str, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                categoryText.setText(str[item]);
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        return dialog;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // get permission to access user gallery

        if(resultCode != RESULT_OK)
            return;

        switch (requestCode) {

            case RESULT_GALLERY:
                if(data!=null) {
                    uri = data.getData();
                    File file = new File(getRealPathFromURI(uri));
                    //File file = new File(getPath(getApplicationContext(), uri));
                    RequestBody fileBody = RequestBody.create(MediaType.parse(getContentResolver().getType(uri)), file);
                    //photo.put("UploadFile\"; filename=\"photo"+ imageIdx++ +".jpg\"", fileBody);
                    photo.put("UploadFile\"; filename=\""+ idx++ +file.getName()+"\"", fileBody);
                    Log.d("Photo Path : ", fileBody.toString());
                    ImageView imageView = new ImageView(this);
                    imageView.setImageURI(uri);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.new_post_image_size), (int) getResources().getDimension(R.dimen.new_post_image_size)));
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                    params.gravity = Gravity.CENTER;
                    imageView.setPadding(0, 0, 8, 0);
                    if (imageListLayout.getVisibility() == View.GONE) {
                        imageListLayout.setVisibility(View.VISIBLE);
                    }
                    imageListLayout.addView(imageView);
                }
                break;

        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String path;
        Cursor cursor = getContentResolver()
                .query(contentURI, null, null, null, null);
        if (cursor == null)
            path=contentURI.getPath();

        else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            path=cursor.getString(idx);

        }
        if(cursor!=null)
            cursor.close();
        return path;
    }


    public void getImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, RESULT_GALLERY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getImageFromGallery();
                    return;
                }
                Toast.makeText(getApplicationContext(), "permission denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
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

    @Override
    public void onBackPressed() {
        setResult(POST_CANCEL);
        super.onBackPressed();
    }
}
