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
import com.android.capstone.yolo.layer.profile.ProfileFragment;
import com.android.capstone.yolo.model.Reply;

import java.util.List;

/**
 * Created by sung9 on 2017-05-04.
 */

public class ProfileReplyAdapter extends RecyclerView.Adapter<ProfileReplyAdapter.ViewHolder> {

    private Context context;
    private List<Reply> reply;
    //private String[] reply;
    //private String[] category;
    private String[] date;

    public ProfileReplyAdapter(Context _context, List<Reply> _reply){
        context = _context;
        reply = _reply;
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
                intent.putExtra("postID", reply.get(pos).getBoardId());
                context.startActivity(intent);
            }
        };

        holder.title.setText(reply.get(pos).getContent());
        holder.title.setOnClickListener(listener);
        holder.writer.setText(ProfileFragment.userName);
        holder.type.setText("");
        holder.date.setText(reply.get(pos).getDate());
    }

    @Override
    public int getItemCount() {

        if(reply==null)
            return 0;
        return reply.size();
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