package com.android.capstone.yolo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.layer.festival.YoutubeActivity;
import com.android.capstone.yolo.model.Music;
import com.android.capstone.yolo.model.YoutubeVideo;
import com.android.capstone.yolo.service.MusicService;
import com.squareup.picasso.Picasso;

import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sung9 on 2017-06-07.
 */

public class MusicSearchResultAdapter extends RecyclerView.Adapter<MusicSearchResultAdapter.ViewHolder> {

    private Context context;
    private List<YoutubeVideo> music;
    //private String videoId = "iQpGq4HguVs";

    public MusicSearchResultAdapter(Context _context, List<YoutubeVideo> _music){
        context = _context;
        music = _music;
    }

    @Override
    public MusicSearchResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music_search_result, parent, false);
        MusicSearchResultAdapter.ViewHolder holder = new MusicSearchResultAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MusicSearchResultAdapter.ViewHolder holder, int position) {
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

        //holder.tag_music.setTags(music_tag[pos]);
        //holder.tag_music.setOnClickListener(tag_listener);
        //Log.i("Tag Test", music_tag[pos].toString());
    }

    @Override
    public int getItemCount() {
        if(music==null)
            return 0;
        return music.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView img_music;
        private TextView tv_music_title;
        public ViewHolder(View view){
            super(view);
            img_music = (ImageView) view.findViewById(R.id.img_music_search_result);
            tv_music_title = (TextView) view.findViewById(R.id.text_music_search_result_title);
        }

    }


}

