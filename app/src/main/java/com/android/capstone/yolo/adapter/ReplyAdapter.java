package com.android.capstone.yolo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.model.Reply;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class ReplyViewHolder extends RecyclerView.ViewHolder{
    TextView writer, date, content;
    CircleImageView profile;

    public ReplyViewHolder(View view){
        super(view);

        writer = (TextView) view.findViewById(R.id.reply_writer);
        date = (TextView) view.findViewById(R.id.reply_date);
        content = (TextView) view.findViewById(R.id.reply_content);
        profile = (CircleImageView) view.findViewById(R.id.reply_profile);
    }
}

public class ReplyAdapter extends RecyclerView.Adapter<ReplyViewHolder> implements AdapterView.OnItemClickListener{
    Context context;
    List<Reply> lists;

    public ReplyAdapter(Context context){
        this.context = context;
    }

    public void setSource(List<Reply> list) {
        this.lists = list;
        this.notifyDataSetChanged();
    }

    @Override
    public ReplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reply, parent, false);
        return new ReplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReplyViewHolder holder, int position) {
        holder.writer.setText(lists.get(position).getUser());
        holder.date.setText(lists.get(position).getDate());
        holder.content.setText(lists.get(position).getContent());
        //Picasso.with(context).load(lists.get(position).getPath()).into(holder.profile);
    }

    @Override
    public long getItemId(int position) { return 0; }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
