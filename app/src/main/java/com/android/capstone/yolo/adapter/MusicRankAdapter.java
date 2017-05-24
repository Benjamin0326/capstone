package com.android.capstone.yolo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.layer.festival.YoutubeActivity;
import com.android.capstone.yolo.model.Music;
import com.squareup.picasso.Picasso;


import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;

/**
 * Created by sung9 on 2017-05-24.
 */

public class MusicRankAdapter extends RecyclerView.Adapter<MusicRankAdapter.ViewHolder> {

    private Context context;
    private List<Music> music;

    public MusicRankAdapter(Context _context, List<Music> _music){
        context = _context;
        music = _music;
    }

    @Override
    public MusicRankAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music_chart, parent, false);
        MusicRankAdapter.ViewHolder holder = new MusicRankAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(MusicRankAdapter.ViewHolder holder, int position) {
        final int pos = position;
        /*
        ImageView.OnClickListener img_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, YoutubeActivity.class);
                intent.putExtra("vidieId", music_img[pos]);
                context.startActivity(intent);
            }
        };*/
        TextView.OnClickListener tv_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

        Picasso.with(context).load(music.get(pos).getAlbumcover()).fit().centerCrop().into(holder.img_music);
        //holder.img_music.setOnClickListener(img_listener);
        holder.tv_music_name.setText(music.get(pos).getTitle());
        holder.tv_artist_name.setText(music.get(pos).getArtist());
        holder.tv_rank.setText(music.get(pos).getRank());
        //holder.tv_music_name.setOnClickListener(tv_listener);
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
        private TextView tv_music_name, tv_artist_name, tv_rank;
        public ViewHolder(View view){
            super(view);
            img_music = (ImageView) view.findViewById(R.id.img_music_cover);
            tv_music_name = (TextView) view.findViewById(R.id.text_music_name);
            tv_artist_name = (TextView) view.findViewById(R.id.text_artist_name);
            tv_rank = (TextView) view.findViewById(R.id.text_music_rank);
        }
    }
}
