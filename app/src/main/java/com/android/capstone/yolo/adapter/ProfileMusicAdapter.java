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
import android.widget.Toast;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.layer.festival.YoutubeActivity;
import com.android.capstone.yolo.model.Music;
import com.android.capstone.yolo.model.YoutubeVideo;
import com.android.capstone.yolo.service.MusicService;
import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;
import com.squareup.picasso.Picasso;

import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sung9 on 2017-05-04.
 */

public class ProfileMusicAdapter extends RecyclerView.Adapter<ProfileMusicAdapter.ViewHolder> {

    private Context context;
    private List<Music> music;
    //private String videoId = "iQpGq4HguVs";

    public ProfileMusicAdapter(Context _context, List<Music> _music){
        context = _context;
        music = _music;
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
                getYoutubeVideoId(pos);
            }
        };
        TextView.OnClickListener tv_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

        Picasso.with(context).load(music.get(pos).getAlbumcover()).fit().centerCrop().into(holder.img_music);
        holder.img_music.setOnClickListener(img_listener);
        holder.tv_music_name.setText(music.get(pos).getTitle());
        holder.tv_music_name.setOnClickListener(tv_listener);
        holder.tv_music_artist.setText(music.get(pos).getArtist());
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
        private TextView tv_music_name, tv_music_artist;
        private TagContainerLayout tag_music;
        public ViewHolder(View view){
            super(view);
            img_music = (ImageView) view.findViewById(R.id.image_item_profile_music);
            tv_music_name = (TextView) view.findViewById(R.id.text_item_profile_music_name);
            tv_music_artist = (TextView) view.findViewById(R.id.text_item_profile_music_artist);
            tag_music = (TagContainerLayout) view.findViewById(R.id.tag__item_profile_music);
        }

    }

    public void getYoutubeVideoId(final int pos){
        //Log.d("Music ID & Token : ", music.get(pos).get_id()+" "+MainActivity.token);
        MusicService service = network.buildRetrofit().create(MusicService.class);
        Call<YoutubeVideo> videoIdCall = service.getVideoId(music.get(pos).getArtist(), music.get(pos).getTitle());

        videoIdCall.enqueue(new Callback<YoutubeVideo>() {
            @Override
            public void onResponse(Call<YoutubeVideo> call, Response<YoutubeVideo> response) {
                if(response.isSuccessful()){
                    YoutubeVideo tmp_video = response.body();
                    if(tmp_video.getVideoId()==null){
                        Toast.makeText(context, "Youtube 비디오를 찾을 수 없습니다.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Log.d("Youtube Video : ", tmp_video.getVideoId());
                    //Toast.makeText(context, "좋아요가 반영되었습니다.", Toast.LENGTH_LONG).show();
                    //String[] tmp = {"1"};
                    //music.get(pos).setLike(tmp);
                    Intent intent = new Intent(context, YoutubeActivity.class);
                    intent.putExtra("videoId", tmp_video.getVideoId());
                    context.startActivity(intent);
                    //for(int i=0;i<festivalLists.get(position).getVideo().length;i++){
                    //    Log.d("#Test :", festivalLists.get(position).getVideo()[i]);
                    //}
                    //Picasso.with(getActivity()).load(festivalLists.get(position).getImg()[1]).into(img);
                    return;
                }
                int code = response.code();
                Log.d("TEST", "err code : " + code);
            }

            @Override
            public void onFailure(Call<YoutubeVideo> call, Throwable t) {
                Toast.makeText(context, "Failed to Like", Toast.LENGTH_LONG).show();
                Log.i("TEST","err : "+ t.getMessage());
            }
        });
    }
}
