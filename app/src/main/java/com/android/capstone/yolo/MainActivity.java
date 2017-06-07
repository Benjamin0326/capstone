package com.android.capstone.yolo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.layer.community.CommunityListFrag;
import com.android.capstone.yolo.layer.login.LoginActivity;
import com.android.capstone.yolo.layer.login.SplashActivity;
import com.android.capstone.yolo.layer.music.MusicFragment;
import com.android.capstone.yolo.layer.profile.ProfileFragment;
import com.android.capstone.yolo.layer.search.SearchActivity;
import com.android.capstone.yolo.model.Login;
import com.android.capstone.yolo.model.ProfileImage;
import com.android.capstone.yolo.service.LoginService;
import com.android.capstone.yolo.service.ProfileService;
import com.squareup.picasso.Picasso;

import java.util.Stack;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    public static BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    public static String token;
    private Login loginInfo;
    public static final int CHECK_LOGIN = 4444;
    public static final int SUCCESS_LOGIN = 1234;
    public static final int FAIL_LOGIN = 4321;
    public static final String RETURN_RESULT = "FLAG";
    public static final String PREF = "YOLO_PREF";
    public static SharedPreferences pref;
    private Intent intent;
    public static Stack menuStack = new Stack();
    public static int menuFlag = 0;
    private boolean doubleBackToExitPressedOnce=false;
    public static boolean remainBackStack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
        startActivity(intent);

        token = getPreferences();
        if(token==null){
            intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, CHECK_LOGIN);
        }else{
            String id = pref.getString("id", null);
            String pw = pref.getString("pw", null);
            getProfileImage();
            postLogin(id, pw);
        }

        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new MainFragment();
        fragmentTransaction.add(R.id.container_fragment, fragment);
        fragmentTransaction.commit();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        //bottomNavigationView.setBackgroundResource(R.color.menu);
        //bottomNavigationView.setItemBackgroundResource(R.color.menu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                final Fragment fr;
                switch (item.getItemId()){
                    case R.id.action_community:
                        menuStack.push(R.id.action_community);
                        if(menuFlag==1){
                            menuFlag=0;
                            break;
                        }
                        fr = new CommunityListFrag();
                        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container_fragment, fr).commit();
                        break;
                    case R.id.action_home:
                        if(menuFlag==1){
                            menuFlag=0;
                            break;
                        }
                        while(menuStack.size()>0){
                            menuStack.pop();
                        }
                        int cnt = fragmentManager.getBackStackEntryCount();
                        for(int i=0; i<cnt; i++){
                            fragmentManager.popBackStack();
                        }
                        fr=new MainFragment();
                        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container_fragment, fr).commit();

                        break;
                    case R.id.action_music:
                        menuStack.push(R.id.action_music);
                        if(menuFlag==1){
                            menuFlag=0;
                            break;
                        }
                        fr = new MusicFragment();
                        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container_fragment, fr).commit();
                        break;
                    case R.id.action_profile:
                        menuStack.push(R.id.action_profile);
                        if(menuFlag==1){
                            menuFlag=0;
                            break;
                        }
                        fr=new ProfileFragment();
                        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container_fragment, fr).commit();
                        break;
                    case R.id.action_search:
                        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    private String getPreferences(){
        pref = getSharedPreferences(PREF, MODE_PRIVATE);
        return pref.getString("token", null);
    }

    @Override
    public void onBackPressed() {
        if(menuStack.size()==0) {
            if(remainBackStack){
                super.onBackPressed();
                remainBackStack=false;
                return;
            }
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
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
        else{
            super.onBackPressed();
            menuStack.pop();
            if(menuStack.size()==0){
                menuFlag = 1;
                bottomNavigationView.setSelectedItemId(R.id.action_home);
            }
            else{
                int tmp = (int)menuStack.pop();
                menuFlag = 1;
                bottomNavigationView.setSelectedItemId(tmp);
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHECK_LOGIN){
            if(resultCode == RESULT_CANCELED){
                intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, CHECK_LOGIN);
            }
            else if(resultCode == RESULT_OK){
                int chk = data.getIntExtra(RETURN_RESULT, FAIL_LOGIN);
                if(chk==FAIL_LOGIN){
                    intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, CHECK_LOGIN);
                }
            }
        }
    }

    public void postLogin(String id, String pw){

        LoginService service = network.buildRetrofit().create(LoginService.class);
        Call<Login> loginCall = service.postLogin("", id, pw);
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

                    SharedPreferences.Editor editor = pref.edit();
                    MainActivity.token = loginInfo.getUser_token();
                    editor.putString("token", loginInfo.getUser_token());
                    editor.commit();
                    return;
                }
                int code = response.code();
                Log.d("TEST", "err code : " + code);
                Toast.makeText(MainActivity.this, "err code : " + code, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to Access", Toast.LENGTH_LONG).show();
                Log.i("TEST","err : "+ t.getMessage());
            }
        });
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
                    SharedPreferences.Editor editor = MainActivity.pref.edit();
                    editor.putString("name", tmp.getName());
                    editor.commit();
                    ProfileFragment.userName = response.body().getName();
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
                Toast.makeText(MainActivity.this, "Failed to Save Profile Image", Toast.LENGTH_LONG).show();
                Log.i("TEST", "err : " + t.getMessage());
            }
        });
    }
}
