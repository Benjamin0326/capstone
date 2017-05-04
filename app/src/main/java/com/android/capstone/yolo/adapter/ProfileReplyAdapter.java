package com.android.capstone.yolo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.capstone.yolo.R;

/**
 * Created by sung9 on 2017-05-04.
 */

public class ProfileReplyAdapter extends RecyclerView.Adapter<ProfileReplyAdapter.ViewHolder> {

    private Context context;
    private String[] reply;
    //private String[] category;
    private String[] date;

    public ProfileReplyAdapter(Context _context, String[] _reply, String[] _date){
        context = _context;
        reply = _reply;
        //category = _category;
        date = _date;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_reply, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int pos = position;
        TextView.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

        //holder.tv_category.setText(category[pos]);
        //holder.tv_category.setOnClickListener(listener);
        holder.tv_reply.setText(reply[pos]);
        holder.tv_reply.setOnClickListener(listener);
        holder.tv_date.setText(date[pos]);
        holder.tv_date.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return reply.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_category;
        private TextView tv_reply;
        private TextView tv_date;
        public ViewHolder(View view){
            super(view);
            tv_category = (TextView) view.findViewById(R.id.text_profile_reply_category);
            tv_reply = (TextView) view.findViewById(R.id.text_profile_reply);
            tv_date = (TextView) view.findViewById(R.id.text_profile_reply_date);
        }

    }
}