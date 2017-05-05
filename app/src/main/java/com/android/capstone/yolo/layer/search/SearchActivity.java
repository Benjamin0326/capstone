package com.android.capstone.yolo.layer.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.BoardListAdapter;
import com.android.capstone.yolo.adapter.SearchHistoryAdapter;
import com.android.capstone.yolo.model.CommunityList;
import com.android.capstone.yolo.model.SearchResult;
import com.android.capstone.yolo.scenario.scenario;

import java.util.ArrayList;
import java.util.List;

import static com.android.capstone.yolo.R.id.searchEditText;

public class SearchActivity extends AppCompatActivity{
    private final String SEARCH_HISTORY = "SEARCH_HISTORY";
    private final int HISTORY_MAX_NUM = 10;
    private boolean isSearched = false;
    private InputMethodManager inputMethodManager;
    private SharedPreferences preferences;
    SearchHistoryAdapter adapter;
    BoardListAdapter boardListAdapter;
    ListView historyList, resultBoardList;
    ArrayList<String> historys, boardList;
    List<CommunityList> communityList;
    LinearLayout searchHistoryLayout, searchTab, searchResultLayout;
    FrameLayout container;
    EditText searchText;
    ImageView searchBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        container = (FrameLayout) findViewById(R.id.exploreContainer);

        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        adapter = new SearchHistoryAdapter(getApplicationContext());
        historyList = (ListView) findViewById(R.id.searchHistoryList);
        historyList.setAdapter(adapter);

        boardListAdapter = new BoardListAdapter(getApplicationContext());
        resultBoardList = (ListView) findViewById(R.id.resultBoardList);
        resultBoardList.setAdapter(boardListAdapter);

        searchTab = (LinearLayout) findViewById(R.id.searchTab);
        searchHistoryLayout = (LinearLayout) findViewById(R.id.searchHistoryLayout);
        searchResultLayout = (LinearLayout) findViewById(R.id.searchResultLayout);
        searchText = (EditText) findViewById(searchEditText);
        searchBtn = (ImageView) findViewById(R.id.searchImage);
        //selectBoard = (MaterialSpinner) findViewById(R.id.selectBoard);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search(searchText.getText().toString());
            }
        });

        searchText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (view.hasFocus()) {
                    searchHistoryLayout.setVisibility(View.VISIBLE);
                    searchResultLayout.setVisibility(View.GONE);
                }
            }
        });
        initView();
    }

    public void initView(){
        initSearchHistory();
        //initSpinner();
        searchText.requestFocus();
    }

    public void initSpinner(){
        communityList = scenario.getCommunityList();
        boardList = new ArrayList<>();
        for(int i=0; i<communityList.size(); i++)
            boardList.add(communityList.get(i).getTitle());

        //selectBoard.setItems(boardList);
        //selectBoard.setSelectedIndex(0);
    }

    public void initSearchHistory(){
        historys = new ArrayList<>();
        preferences = getApplicationContext().getSharedPreferences(SEARCH_HISTORY, Context.MODE_PRIVATE);
        Log.d("TEST", "size : " + preferences.getAll().size());
        for(int i=0; i<Math.min(preferences.getAll().size(), HISTORY_MAX_NUM - 1); i++){
            String history = new String(preferences.getString(""+i, null));
            Log.d("TEST", "item : " + history);
            historys.add(history);
        }
        adapter.setSource(historys);
    }

    public void search(final String query){

        isSearched = true;

        searchText.setText(query);
        searchTab.requestFocus();

        hideKeyboard(searchText);

        SearchResult result = scenario.search(query);

        if (searchHistoryLayout.getVisibility() == View.VISIBLE) {

            searchHistoryLayout.setVisibility(View.GONE);
            searchResultLayout.setVisibility(View.VISIBLE);
        }

        boardListAdapter.setSource(result.getBoardLists());
        updateSearchHistory(query);
        /*
        SearchService service = network.buildRetrofit().create(SearchService.class);
        Call<SearchResult> call = service.search(query);
        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if(response.isSuccessful()){
                    response.body();
                    updateSearchHistory(query);
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Log.d("TEST", "err : " + t.getMessage().toString());
            }
        });
        */
    }

    public void updateSearchHistory(String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            SharedPreferences preferences = getApplicationContext().getSharedPreferences(SEARCH_HISTORY, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            for (int i = Math.min(preferences.getAll().size(), HISTORY_MAX_NUM - 1); i >= 0; i--) {
                editor.putString("" + (i + 1), preferences.getString("" + i, null));
            }
            editor.putString("" + 0, keyword);
            editor.commit();

            historys.add(0, keyword);
            if(historys.size() > HISTORY_MAX_NUM){
                for(int i=HISTORY_MAX_NUM; i<historys.size(); i++)
                    historys.remove(i);
            }

            Log.d("TEST", "save : " + keyword);
            adapter.setSource(historys);
        }
    }

    public void moveToResultView(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.exploreContainer, new SearchResultFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void hideKeyboard(View view) {

        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onBackPressed() {

        if (searchHistoryLayout.getVisibility() == View.VISIBLE) {

            if (!isSearched) {

                super.onBackPressed();
                return;
            }

            searchHistoryLayout.setVisibility(View.GONE);
            searchResultLayout.setVisibility(View.VISIBLE);

            searchTab.requestFocus();

            return;
        }

        if (searchResultLayout.getVisibility() == View.VISIBLE) {

            Fragment fragment = getSupportFragmentManager().findFragmentByTag(SearchResultFragment.class.getName());

            if (fragment != null) {

                super.onBackPressed();
                return;
            }

            isSearched = false;

            searchHistoryLayout.setVisibility(View.VISIBLE);
            searchResultLayout.setVisibility(View.GONE);

            return;
        }

        super.onBackPressed();
    }
}
