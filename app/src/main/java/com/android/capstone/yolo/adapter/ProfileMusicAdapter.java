package com.android.capstone.yolo.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.layer.festival.YoutubeActivity;
import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;
import com.squareup.picasso.Picasso;

import co.lujun.androidtagview.TagContainerLayout;

/**
 * Created by sung9 on 2017-05-04.
 */

public class ProfileMusicAdapter extends RecyclerView.Adapter<ProfileMusicAdapter.ViewHolder> {

    private Context context;
    private String[] music_img;
    private String[] music_name;
    private String[][] music_tag;

    public ProfileMusicAdapter(Context _context, String[] _music_img, String[] _music_name, String[][] _music_tag){
        context = _context;
        music_img = _music_img;
        music_name = _music_name;
        music_tag = _music_tag;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_music, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int pos = position;
        ImageView.OnClickListener img_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, YoutubeActivity.class);
                intent.putExtra("vidieId", music_img[pos]);
                context.startActivity(intent);
            }
        };
        TextView.OnClickListener tv_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

        Picasso.with(context).load("http://img.youtube.com/vi/"+music_img[pos]+"/default.jpg").fit().centerCrop().into(holder.img_music);
        holder.img_music.setOnClickListener(img_listener);
        holder.tv_music_name.setText(music_name[pos]);
        holder.tv_music_name.setOnClickListener(tv_listener);
        holder.tag_music.setTags(music_tag[pos]);
        //holder.tag_music.setOnClickListener(tag_listener);
        Log.i("Tag Test", music_tag[pos].toString());
    }

    @Override
    public int getItemCount() {
        return music_name.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView img_music;
        private TextView tv_music_name;
        private TagContainerLayout tag_music;
        public ViewHolder(View view){
            super(view);
            img_music = (ImageView) view.findViewById(R.id.image_item_profile_music);
            tv_music_name = (TextView) view.findViewById(R.id.text_item_profile_music_name);
            tag_music = (TagContainerLayout) view.findViewById(R.id.tag__item_profile_music);
        }

    }
}
