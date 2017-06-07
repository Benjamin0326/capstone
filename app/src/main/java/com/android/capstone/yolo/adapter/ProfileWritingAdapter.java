package com.android.capstone.yolo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.layer.community.BoardDetailActivity;
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board_list, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int pos = position;
        TextView.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BoardDetailActivity.class);
                intent.putExtra("postID", post.get(pos).get_id());
                context.startActivity(intent);
            }
        };

        holder.title.setText(post.get(pos).getTitle());
        holder.title.setOnClickListener(listener);
        holder.type.setText("["+post.get(pos).getTag()+"]");
        holder.writer.setText(post.get(pos).getUser());
        holder.date.setText(post.get(pos).getDate());

    }

    @Override
    public int getItemCount() {
        if(post==null)
            return 0;
        return post.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title, type, writer, date;
        public ViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.board_title);
            type = (TextView) view.findViewById(R.id.board_type);
            writer = (TextView) view.findViewById(R.id.board_writer);
            date = (TextView) view.findViewById(R.id.board_date);
        }

    }
}
