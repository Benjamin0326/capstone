package com.android.capstone.yolo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.capstone.yolo.R;

import java.util.ArrayList;

public class SearchHistoryAdapter extends BaseAdapter{
    private static SearchHistoryAdapter instance;
    private ArrayList<String> lists;
    private Context context;

    public SearchHistoryAdapter(Context context){
        SearchHistoryAdapter.instance = this;
        this.context = context;
    }

    public static SearchHistoryAdapter getInstance(){
        return instance;
    }

    public void append(String list) {
        this.lists.add(0, list);
        this.notifyDataSetChanged();
    }

    public void setSource(ArrayList<String> list) {
        this.lists = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() { return lists != null ? lists.size() : 0; }

    @Override
    public Object getItem(int position) {
        return (lists != null && (0 <= position && position < lists.size()))
                ? lists.get(position) : null;
    }

    @Override
    public long getItemId(int position) { return 0; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = View.inflate(parent.getContext(), R.layout.item_search_history, null);

        TextView history = (TextView) convertView.findViewById(R.id.historyItemName);
        history.setText(lists.get(position));

        return convertView;
    }
}
