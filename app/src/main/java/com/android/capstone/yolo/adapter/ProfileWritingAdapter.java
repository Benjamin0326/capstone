package com.android.capstone.yolo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.model.Post;

import java.util.List;

/**
 * Created by sung9 on 2017-05-04.
 */

public class ProfileWritingAdapter extends RecyclerView.Adapter<ProfileWritingAdapter.ViewHolder> {

    private Context context;
    private List<Post> post;

    public ProfileWritingAdapter(Context _context, List<Post> _post){
        context = _context;
        post = _post;
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

        holder.tv_category.setText(post.get(pos).getTag());
        holder.tv_category.setOnClickListener(listener);
        holder.tv_subject.setText(post.get(pos).getTitle());
        holder.tv_subject.setOnClickListener(listener);
        holder.tv_date.setText(post.get(pos).getDate());
        holder.tv_date.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        if(post==null)
            return 0;
        return post.size();
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
