package com.android.capstone.yolo.layer.community;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.capstone.yolo.BaseActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.CommunityPagerAdapter;
import com.android.capstone.yolo.layer.search.SearchActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class CommunityBoardActivity extends BaseActivity{
    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton postBtn, searchBtn;
    String communityID, communityTitle;
    TextView title;
    final int POST_FLAG = 2;
    final int POST_CANCEL = 3;
    CommunityPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_board);

        initView();
    }

    public void initView(){
        tabLayout = (TabLayout) findViewById(R.id.tab_community_board);
        tabLayout.addTab(tabLayout.newTab().setText("전체 게시판"));
        tabLayout.addTab(tabLayout.newTab().setText("인기 게시판"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager_community_board);

        pagerAdapter = new CommunityPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.fab_menu);
        postBtn = (FloatingActionButton) findViewById(R.id.menu_write_post);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (floatingActionMenu.isOpened())
                    floatingActionMenu.close(true);

                Intent intent = new Intent(getApplicationContext(), NewPostActivity.class);
                intent.putExtra("communityID", communityID);
                startActivityForResult(intent, POST_FLAG);
            }
        });
        searchBtn = (FloatingActionButton) findViewById(R.id.menu_search);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (floatingActionMenu.isOpened())
                    floatingActionMenu.close(true);

                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        title = (TextView) findViewById(R.id.com_board_title);
        communityID = getIntent().getExtras().getString("communityID");
        communityTitle = getIntent().getExtras().getString("communityTitle");
        title.setText(communityTitle);
        //getPostList();
    }

    @Override
    public void onBackPressed() {
        if (floatingActionMenu.isOpened())
            floatingActionMenu.close(true);
        else
            super.onBackPressed();
    }

    public boolean isFloatingMenuOpened(){
        if(floatingActionMenu.isOpened())
            return true;
        return false;
    }

    public void closeFloatingMenu(){
        floatingActionMenu.close(true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == POST_FLAG && viewPager.getCurrentItem() == 0){
            if(resultCode == POST_FLAG) {
                pagerAdapter.getAllFragment().getPostList(communityID);
                return;
            }
            if(resultCode == POST_CANCEL)
                return;
        }

        Toast.makeText(getApplicationContext(), "글 목록을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();

    }

    public String getCommunityID(){
        return communityID;
    }
}
