package com.android.capstone.yolo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.model.CommunityList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommunityListAdapter extends BaseAdapter{

    private static CommunityListAdapter instance;
    private List<CommunityList> lists;
    private Context context;

    public CommunityListAdapter(Context context){
        CommunityListAdapter.instance = this;
        this.context = context;
    }

    public static CommunityListAdapter getInstance(){
        return instance;
    }

    public void append(CommunityList list) {
        this.lists.add(0, list );
        this.notifyDataSetChanged();
    }

    public void setSource(List<CommunityList> list) {
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

        convertView = View.inflate(parent.getContext(), R.layout.item_community_list, null);
        TextView title = (TextView) convertView.findViewById(R.id.com_list_title);
        TextView sub = (TextView) convertView.findViewById(R.id.com_list_sub);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.com_list_thumbnail);

        CommunityList list = lists.get(position);

        title.setText(list.title);
        sub.setText(list.sub);
        Picasso.with(context).load(list.getPath()).into(imageView);

        return convertView;
    }
}
