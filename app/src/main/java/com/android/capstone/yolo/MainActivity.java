package com.android.capstone.yolo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.android.capstone.yolo.layer.community.list.CommunityListFrag;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = new MainFragment();
        fragmentTransaction.add(R.id.container_fragment, fragment);
        fragmentTransaction.commit();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.action_home);
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

                        break;
                    case R.id.action_search:

                        break;
                }
                return true;
            }
        });
    }
}
