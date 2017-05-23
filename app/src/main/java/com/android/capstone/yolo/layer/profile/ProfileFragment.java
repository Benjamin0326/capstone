package com.android.capstone.yolo.layer.profile;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.capstone.yolo.MainActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.ProfilePagerAdapter;
import com.android.capstone.yolo.layer.login.LoginActivity;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

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
        Uri imageUri = getUriPreferences();
        if(imageUri!=null)
            Picasso.with(getActivity()).load(imageUri).into(thumbnail);

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

}
