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

public class ProfileWritingAdapter extends RecyclerView.Adapter<ProfileWritingAdapter.ViewHolder> {

    private Context context;
    private String[] subject;
    private String[] category;
    private String[] date;

    public ProfileWritingAdapter(Context _context, String[] _subject, String[] _category, String[] _date){
        context = _context;
        subject = _subject;
        category = _category;
        date = _date;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_writing, parent, false);
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

        holder.tv_category.setText(category[pos]);
        holder.tv_category.setOnClickListener(listener);
        holder.tv_subject.setText(subject[pos]);
        holder.tv_subject.setOnClickListener(listener);
        holder.tv_date.setText(date[pos]);
        holder.tv_date.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return subject.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_category;
        private TextView tv_subject;
        private TextView tv_date;
        public ViewHolder(View view){
            super(view);
            tv_category = (TextView) view.findViewById(R.id.text_profile_writing_category);
            tv_subject = (TextView) view.findViewById(R.id.text_profile_writing_subject);
            tv_date = (TextView) view.findViewById(R.id.text_profile_writing_date);
        }

    }
}
