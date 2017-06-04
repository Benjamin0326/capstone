package com.android.capstone.yolo.layer.search;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.capstone.yolo.BaseActivity;
import com.android.capstone.yolo.MainActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.adapter.BoardListAdapter;
import com.android.capstone.yolo.adapter.SearchHistoryAdapter;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.layer.community.BoardDetailActivity;
import com.android.capstone.yolo.model.BoardList;
import com.android.capstone.yolo.service.SearchService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.android.capstone.yolo.R.id.searchEditText;

public class SearchActivity extends BaseActivity {
    private final String SEARCH_HISTORY = "SEARCH_HISTORY";
    private final int HISTORY_MAX_NUM = 10;
    private boolean isSearched = false;
    private InputMethodManager inputMethodManager;
    private SharedPreferences preferences;
    SearchHistoryAdapter adapter;
    BoardListAdapter boardListAdapter;
    ListView historyList, resultBoardList;
    ArrayList<String> historys;
    LinearLayout searchHistoryLayout, searchTab, searchResultLayout;
    FrameLayout container, categoryLayout;
    EditText searchText;
    ImageView searchBtn;
    TextView categoryText;
    AlertDialog categoryDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        MainActivity.menuFlag = 1;
        int tmp;
        if(MainActivity.menuStack.size()!=0)
             tmp = (int)MainActivity.menuStack.pop();
        else
            tmp = R.id.action_home;
        MainActivity.bottomNavigationView.setSelectedItemId(tmp);

        container = (FrameLayout) findViewById(R.id.exploreContainer);

        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        adapter = new SearchHistoryAdapter(getApplicationContext());
        historyList = (ListView) findViewById(R.id.searchHistoryList);
        historyList.setAdapter(adapter);
        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String query = adapter.getItem(i).toString();
                searchText.setText(query);
                search(query);
            }
        });

        boardListAdapter = new BoardListAdapter(getApplicationContext());
        resultBoardList = (ListView) findViewById(R.id.resultBoardList);
        resultBoardList.setAdapter(boardListAdapter);
        resultBoardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BoardList boardList = (BoardList) boardListAdapter.getItem(i);
                Intent intent = new Intent(getApplicationContext(), BoardDetailActivity.class);
                intent.putExtra("postID", boardList.getId());
                startActivity(intent);
            }
        });

        searchTab = (LinearLayout) findViewById(R.id.searchTab);
        searchHistoryLayout = (LinearLayout) findViewById(R.id.searchHistoryLayout);
        searchResultLayout = (LinearLayout) findViewById(R.id.searchResultLayout);
        searchText = (EditText) findViewById(searchEditText);
        searchBtn = (ImageView) findViewById(R.id.searchImage);
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

        categoryText = (TextView) findViewById(R.id.search_category_text);
        categoryLayout = (FrameLayout) findViewById(R.id.search_category);
        categoryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryDialog = createDialog();
                categoryDialog.show();
            }
        });

        initView();
    }

    public void initView(){
        initSearchHistory();
        //initSpinner();
        searchText.requestFocus();
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

    public void initSearchHistory(){
        historys = new ArrayList<>();
        preferences = getApplicationContext().getSharedPreferences(SEARCH_HISTORY, Context.MODE_PRIVATE);
        for(int i=0; i<Math.min(preferences.getAll().size(), HISTORY_MAX_NUM - 1); i++){
            String history = new String(preferences.getString(""+i, null));
            historys.add(history);
        }
        adapter.setSource(historys);
    }

    public void search(final String query){

        isSearched = true;

        searchText.setText(query);
        searchTab.requestFocus();

        hideKeyboard(searchText);

        //SearchResult result = scenario.search(query);

        if (searchHistoryLayout.getVisibility() == View.VISIBLE) {

            searchHistoryLayout.setVisibility(View.GONE);
            searchResultLayout.setVisibility(View.VISIBLE);
        }


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
        SearchService searchService = network.buildRetrofit().create(SearchService.class);
        Call<List<BoardList>> call = searchService.tempSearch();
        call.enqueue(new Callback<List<BoardList>>() {
            @Override
            public void onResponse(Call<List<BoardList>> call, Response<List<BoardList>> response) {
                if(response.isSuccessful()){
                    List<BoardList> lists = response.body();
                    boardListAdapter.setSource(lists);
                    updateSearchHistory(query);
                }

                if(response.code() >= 500){
                    Toast.makeText(getApplicationContext(), "Server err " + response.code() + " : " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BoardList>> call, Throwable t) {
                Log.d("TEST", "err : " + t.getMessage().toString());
            }
        });

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
/*
    public void moveToResultView(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.exploreContainer, new SearchDetailActivity());
        transaction.addToBackStack(null);
        transaction.commit();
    }
*/
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

            Fragment fragment = getSupportFragmentManager().findFragmentByTag(SearchDetailActivity.class.getName());

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
