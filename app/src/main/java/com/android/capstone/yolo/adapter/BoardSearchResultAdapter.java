package com.android.capstone.yolo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.layer.community.BoardDetailActivity;
import com.android.capstone.yolo.model.BoardList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    Map<String, String> communityTitle;

    public BoardSearchResultAdapter(Context context){
        this.context = context;
        communityTitle = new HashMap<>();
        communityTitle.put("593949e56846cc2aa893f3f5", "Ultra Music Festival");
        communityTitle.put("5939535eec4ddc2be104d33c", "World DJ Festival");
        communityTitle.put("593954bdec4ddc2be104d33d", "Heineken Present Stardium");
        communityTitle.put("593955c9ec4ddc2be104d33e", "World Club Dome Korea");
        communityTitle.put("593956e3ec4ddc2be104d340", "Unite with Tomorrowland");
        communityTitle.put("5939590ae3fe1c2cbe6211b7", "Water Bomb");
        communityTitle.put("593959aae3fe1c2cbe6211b8", "Dream Station [Live For Now]");
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
        Log.d("TEST", "type : " + lists.get(position).getType());
        holder.type.setText("["+communityTitle.get(lists.get(position).getType())+"]");
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BoardDetailActivity.class);
                intent.putExtra("postID", lists.get(position).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("communityTitle", communityTitle.get(lists.get(position).getType()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}