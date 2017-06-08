package com.android.capstone.yolo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.capstone.yolo.MainActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.layer.festival.YoutubeActivity;
import com.android.capstone.yolo.model.YoutubeVideo;
import com.android.capstone.yolo.service.MusicService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class MusicViewHolder extends RecyclerView.ViewHolder{
    ImageView img_music, img_like;
    TextView tv_music_title;

    public MusicViewHolder(View view){
        super(view);
        img_music = (ImageView) view.findViewById(R.id.img_music_search_result);
        tv_music_title = (TextView) view.findViewById(R.id.text_music_search_result_title);
        img_like = (ImageView) view.findViewById(R.id.img_music_like);
    }

}

public class MusicSearchResultAdapter extends RecyclerView.Adapter<MusicViewHolder>{

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
    public void onBindViewHolder(final MusicViewHolder holder, int position) {
        final int pos = position;
        ImageView.OnClickListener like_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(music.get(pos).getLike()==0) {
                    insertMusicData(pos);
                    holder.img_like.setImageResource(R.mipmap.icon_item_action_like_selected);
                }
            }
        };

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
        //Picasso.with(context).load(R.mipmap.icon_item_action_like_deselected).fit().centerCrop().into(holder.img_like);
        holder.img_like.setOnClickListener(like_listener);
        if(music.get(pos).getLike()==1)
            holder.img_like.setImageResource(R.mipmap.icon_item_action_like_selected);
    }

    @Override
    public int getItemCount() {
        return music != null ? music.size() : 0;
    }

    public void insertMusicData(final int pos){
        MusicService service = network.buildRetrofit().create(MusicService.class);
        Call<YoutubeVideo> musicLikeCall = service.insertMusic(music.get(pos).getVideoId(), MainActivity.token);

        musicLikeCall.enqueue(new Callback<YoutubeVideo>() {
            @Override
            public void onResponse(Call<YoutubeVideo> call, Response<YoutubeVideo> response) {
                if(response.isSuccessful()){
                    YoutubeVideo tmp_music = response.body();
                    Log.d("Music Like Test : ", tmp_music.getVideoId()+" "+tmp_music.getTitle());
                    ILikeThisMusic(tmp_music.getVideoId());
                    //Toast.makeText(context, "좋아요가 반영되었습니다.", Toast.LENGTH_LONG).show();

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
                Toast.makeText(context, "Failed to Insert Music Data", Toast.LENGTH_LONG).show();
                Log.i("TEST","err : "+ t.getMessage());
            }
        });
    }

    public void ILikeThisMusic(String videoId){
        MusicService service = network.buildRetrofit().create(MusicService.class);
        Call<YoutubeVideo> musicLikeCall = service.iLikeIt(videoId, MainActivity.token);

        musicLikeCall.enqueue(new Callback<YoutubeVideo>() {
            @Override
            public void onResponse(Call<YoutubeVideo> call, Response<YoutubeVideo> response) {
                if(response.isSuccessful()){
                    YoutubeVideo tmp_music = response.body();
                    Log.d("Music Like Test : ", tmp_music.getVideoId()+" "+tmp_music.getTitle());
                    Toast.makeText(context, "좋아요가 반영되었습니다.", Toast.LENGTH_LONG).show();

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
                Toast.makeText(context, "Failed to Insert Music Data", Toast.LENGTH_LONG).show();
                Log.i("TEST","err : "+ t.getMessage());
            }
        });
    }
}

