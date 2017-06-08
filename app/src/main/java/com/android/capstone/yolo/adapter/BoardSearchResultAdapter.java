package com.android.capstone.yolo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.layer.community.BoardDetailActivity;
import com.android.capstone.yolo.model.BoardList;

import java.util.List;

class BoardViewHolder extends RecyclerView.ViewHolder{
    TextView title, type, writer, date;
    LinearLayout container;

    public BoardViewHolder(View view){
        super(view);

        title = (TextView) view.findViewById(R.id.board_title);
        type = (TextView) view.findViewById(R.id.board_type);
        writer = (TextView) view.findViewById(R.id.board_writer);
        date = (TextView) view.findViewById(R.id.board_date);
        container = (LinearLayout) view.findViewById(R.id.item_board_container);
    }
}

public class BoardSearchResultAdapter extends RecyclerView.Adapter<BoardViewHolder>{
    Context context;
    List<BoardList> lists;

    public BoardSearchResultAdapter(Context context){
        this.context = context;
    }

    public void setSource(List<BoardList> list) {
        this.lists = list;
        this.notifyDataSetChanged();
    }

    @Override
    public BoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board_list, parent, false);
        return new BoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BoardViewHolder holder, final int position) {
        holder.date.setText(lists.get(position).getDate());
        holder.title.setText(lists.get(position).getTitle());
        holder.writer.setText(lists.get(position).getUser());
        holder.type.setText("["+lists.get(position).getType()+"]");
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BoardDetailActivity.class);
                intent.putExtra("postID", lists.get(position).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //intent.putExtra("communityTitle", );
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}