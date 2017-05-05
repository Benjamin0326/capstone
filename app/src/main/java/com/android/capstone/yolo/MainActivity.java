package com.android.capstone.yolo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.android.capstone.yolo.layer.login.LoginActivity;
import com.android.capstone.yolo.layer.profile.ProfileFragment;
import com.android.capstone.yolo.layer.community.list.CommunityListFrag;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private String token;
    public static final int CHECK_LOGIN = 4444;
    public static final int SUCCESS_LOGIN = 1234;
    public static final int FAIL_LOGIN = 4321;
    public static final String RETURN_RESULT = "FLAG";
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        token = getPreferences();
        if(token.compareTo("none")==0){
            intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, CHECK_LOGIN);
        }

        getSupportActionBar().hide();

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
                        fr = new CommunityListFrag();
                        fragmentManager.beginTransaction().replace(R.id.container_fragment, fr).commit();
                        break;
                    case R.id.action_home:
                        fr=new MainFragment();
                        fragmentManager.beginTransaction().replace(R.id.container_fragment, fr).commit();
                        break;
                    case R.id.action_music:

                        break;
                    case R.id.action_profile:
                        fr=new ProfileFragment();
                        fragmentManager.beginTransaction().replace(R.id.container_fragment, fr).commit();
                        break;
                    case R.id.action_search:

                        break;
                }
                return true;
            }
        });
    }

    private String getPreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getString("token", "none");
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
}
