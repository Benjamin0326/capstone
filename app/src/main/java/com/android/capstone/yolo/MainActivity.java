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
import com.android.capstone.yolo.layer.community.CommunityListFrag;
import com.android.capstone.yolo.layer.login.LoginActivity;
import com.android.capstone.yolo.layer.profile.ProfileFragment;
import com.android.capstone.yolo.layer.search.SearchActivity;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    public static BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private String token;
    public static final int CHECK_LOGIN = 4444;
    public static final int SUCCESS_LOGIN = 1234;
    public static final int FAIL_LOGIN = 4321;
    public static final String RETURN_RESULT = "FLAG";
    private Intent intent;
    public static Stack menuStack = new Stack();
    public static int menuFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        token = getPreferences();
        if(token.compareTo("none")==0){
            intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, CHECK_LOGIN);
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
                        menuStack.push(R.id.action_home);
                        if(menuFlag==1){
                            menuFlag=0;
                            break;
                        }
                        fr=new MainFragment();
                        fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.container_fragment, fr).commit();
                        break;
                    case R.id.action_music:
                        if(menuFlag==1){
                            menuFlag=0;
                            break;
                        }
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
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getString("token", "none");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(menuStack.size()==0)
            return;
        else{
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
}
