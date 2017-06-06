package com.android.capstone.yolo.layer.profile;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.capstone.yolo.MainActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.ProfilePagerAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.layer.login.JoinActivity;
import com.android.capstone.yolo.layer.login.LoginActivity;
import com.android.capstone.yolo.model.ProfileImage;
import com.android.capstone.yolo.service.ProfileService;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.android.capstone.yolo.MainActivity.CHECK_LOGIN;
import static com.theartofdev.edmodo.cropper.CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ProfilePagerAdapter pagerAdapter;
    private CircleImageView thumbnail;
    private TextView name, info;
    private Uri mCropImageUri;
    private MultipartBody.Part photo;
    public static String userName;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        thumbnail = (CircleImageView) rootView.findViewById(R.id.thumbnail_profile);
        name = (TextView) rootView.findViewById(R.id.text_profile_name);
        info = (TextView) rootView.findViewById(R.id.text_profile_info);

        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImageClick(thumbnail);
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_profile_menu);
                dialog.setTitle("Profile Menu");

                //TextView textView = (TextView) dialog.findViewById(R.id.text_dialog_festival_picture);
                //textView.setText(pos+"번째 사진입니다.");
                Button btn = (Button) dialog.findViewById(R.id.btn_dialog_logout);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = MainActivity.pref.edit();
                        editor.clear();
                        editor.commit();
                        MainActivity.bottomNavigationView.setSelectedItemId(R.id.action_home);
                        dialog.cancel();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivityForResult(intent, CHECK_LOGIN);
                    }
                });
                dialog.show();
            }
        });

        String id = getIdPreferences();
        if(id!=null)
            name.setText(id);
        getProfileImage();

        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_profile);
        tabLayout.addTab(tabLayout.newTab().setText("음악 리스트"));
        tabLayout.addTab(tabLayout.newTab().setText("작성한 글"));
        tabLayout.addTab(tabLayout.newTab().setText("작성한 댓글"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) rootView.findViewById(R.id.pager_profile);
        pagerAdapter = new ProfilePagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return rootView;
    }


    private Uri getUriPreferences(){
        SharedPreferences pref = getActivity().getSharedPreferences(MainActivity.PREF, MODE_PRIVATE);
        String tmp = pref.getString("profile", "");
        if(tmp.compareTo("")==0){
            return null;
        }
        else{
            Uri uri = Uri.parse(tmp);
            return uri;
        }
    }

    private String getIdPreferences(){
        SharedPreferences pref = getActivity().getSharedPreferences(MainActivity.PREF, MODE_PRIVATE);
        String tmp = pref.getString("id", "");
        if(tmp.compareTo("")==0){
            return null;
        }
        else{
            return tmp;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CropImage.startPickImageActivity(getActivity());
            } else {
                Toast.makeText(getActivity(), "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // required permissions granted, start crop image activity
                startCropImageActivity(mCropImageUri);
            } else {
                Toast.makeText(getActivity(), "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void getProfileImage() {
        ProfileService service = network.buildRetrofit().create(ProfileService.class);
        //String url = getPath(getContext(), uri);
        Call<ProfileImage> profileCall = service.getUserImage(MainActivity.token);
        profileCall.enqueue(new Callback<ProfileImage>() {
            @Override
            public void onResponse(Call<ProfileImage> call, Response<ProfileImage> response) {
                if (response.isSuccessful()) {
                    ProfileImage tmp = response.body();

                    Log.d("Profile Image Info : ", tmp.getImage());
                    Picasso.with(getActivity()).load(tmp.getImage()).into(thumbnail);
                    info.setText(tmp.getName());
                    SharedPreferences.Editor editor = MainActivity.pref.edit();
                    editor.putString("name", response.body().getName());
                    editor.commit();
                    userName = response.body().getName();
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
                Toast.makeText(getContext(), "Failed to Save Profile Image", Toast.LENGTH_LONG).show();
                Log.i("TEST", "err : " + t.getMessage());
            }
        });
    }

    @Override
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(getActivity(), data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(getActivity(), imageUri)) {
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
                Uri resultUri = result.getUri();
                Picasso.with(getActivity()).load(resultUri).into(thumbnail);
                saveUriPreferences(resultUri);
                Log.d("Result Uri : ", resultUri.toString());
                postProfileImage(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void startCropImageActivity(Uri uri) {
        CropImage.activity(uri).setCropShape(CropImageView.CropShape.OVAL).start(getContext(), this);
    }
    public void onSelectImageClick(View view) {
        startActivityForResult(CropImage.getPickImageChooserIntent(getContext()), PICK_IMAGE_CHOOSER_REQUEST_CODE);
    }
    private void saveUriPreferences(Uri _uri){
        SharedPreferences.Editor editor = MainActivity.pref.edit();
        editor.putString("profile", _uri.toString());
        editor.commit();
    }

    public void postProfileImage(Uri uri){
        ProfileService service = network.buildRetrofit().create(ProfileService.class);
        //String url = getPath(getContext(), uri);
        Map<String, RequestBody> map = new HashMap<>();
        File file = new File(uri.getPath());
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        map.put("UploadFile\"; filename=\"photo.jpg\"", fileBody);
        Log.d("User Token : ", MainActivity.token);
        Call<ProfileImage> profileCall = service.postUserImage(map, MainActivity.token);
        profileCall.enqueue(new Callback<ProfileImage>() {
            @Override
            public void onResponse(Call<ProfileImage> call, Response<ProfileImage> response) {
                if(response.isSuccessful()){
                    ProfileImage tmp = response.body();

                    Log.d("Profile Image Info : ", tmp.getImage());
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
                Toast.makeText(getContext(), "Failed to Save Profile Image", Toast.LENGTH_LONG).show();
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
