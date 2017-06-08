package com.android.capstone.yolo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.layer.festival.YoutubeActivity;
import com.android.capstone.yolo.model.YoutubeVideo;
import com.squareup.picasso.Picasso;

import java.util.List;

class MusicViewHolder extends RecyclerView.ViewHolder{
    ImageView img_music;
    TextView tv_music_title;

    public MusicViewHolder(View view){
        super(view);
        img_music = (ImageView) view.findViewById(R.id.img_music_search_result);
        tv_music_title = (TextView) view.findViewById(R.id.text_music_search_result_title);
    }

}

public class MusicSearchResultAdapter extends RecyclerView.Adapter<MusicViewHolder> implements AdapterView.OnItemClickListener{

    private Context context;
    private List<YoutubeVideo> music;

    public MusicSearchResultAdapter(Context _context){
        context = _context;
    }

    public void setSource(List<YoutubeVideo> list) {
        this.music = list;
        this.notifyDataSetChanged();
    }

    @Override
    public MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music_search_result, parent, false);
        MusicViewHolder holder = new MusicViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MusicViewHolder holder, int position) {
        final int pos = position;
        ImageView.OnClickListener img_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, YoutubeActivity.class);
                intent.putExtra("videoId", music.get(pos).getVideoId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        };
        TextView.OnClickListener tv_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, YoutubeActivity.class);
                intent.putExtra("videoId", music.get(pos).getVideoId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        };

        Picasso.with(context).load(music.get(pos).getThumbnail()).fit().centerCrop().into(holder.img_music);
        holder.img_music.setOnClickListener(img_listener);
        holder.tv_music_title.setText(music.get(pos).getTitle());
        holder.tv_music_title.setOnClickListener(tv_listener);
    }

    @Override
    public int getItemCount() {
        return music != null ? music.size() : 0;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }


}

