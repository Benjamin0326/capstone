package com.android.capstone.yolo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.model.BoardList;

import java.text.SimpleDateFormat;
import java.util.List;

public class BoardListAdapter extends BaseAdapter{
    private static BoardListAdapter instance;
    private List<BoardList> lists;
    private Context context;
    private SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    public BoardListAdapter(Context context){
        BoardListAdapter.instance = this;
        this.context = context;
    }

    public static BoardListAdapter getInstance(){
        return instance;
    }

    public void append(BoardList list) {
        this.lists.add(0, list );
        this.notifyDataSetChanged();
    }

    public void setSource(List<BoardList> list) {
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

        convertView = View.inflate(parent.getContext(), R.layout.item_board_list, null);

        TextView title = (TextView) convertView.findViewById(R.id.board_title);
        TextView type = (TextView) convertView.findViewById(R.id.board_type);
        TextView writer = (TextView) convertView.findViewById(R.id.board_writer);
        TextView date = (TextView) convertView.findViewById(R.id.board_date);

        BoardList list = lists.get(position);

        title.setText(list.title);
        type.setText("["+list.getType()+"]");
        writer.setText(list.getWriter());
        date.setText(FORMAT.format(list.getTimestamp()));

        return convertView;
    }
}
