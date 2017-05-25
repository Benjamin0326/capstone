package com.android.capstone.yolo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.capstone.yolo.MainActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.model.Music;
import com.android.capstone.yolo.service.MusicService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sung9 on 2017-05-25.
 */

public class MusicLikeRankAdapter extends RecyclerView.Adapter<MusicLikeRankAdapter.ViewHolder> {

    private Context context;
    private List<Music> music;

    public MusicLikeRankAdapter(Context _context, List<Music> _music){
        context = _context;
        music = _music;
    }

    @Override
    public MusicLikeRankAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music_my_like, parent, false);
        MusicLikeRankAdapter.ViewHolder holder = new MusicLikeRankAdapter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MusicLikeRankAdapter.ViewHolder holder, int position) {
        final int pos = position;

        ImageView.OnClickListener img_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(music.get(pos).get_Like().compareTo("0")==0){
                    //postMusicLike(pos);
                    holder.img_like.setImageResource(R.mipmap.icon_item_action_like_selected);
                }
                /*
                else{
                    holder.img_like.setImageResource(R.mipmap.icon_item_action_like_deselected);
                }
                */
            }
        };
        TextView.OnClickListener tv_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

        Picasso.with(context).load(music.get(pos).getAlbumcover()).fit().centerCrop().into(holder.img_music);
        //holder.img_music.setOnClickListener(img_listener);
        holder.tv_music_name.setText(music.get(pos).getTitle());
        holder.tv_artist_name.setText(music.get(pos).getArtist());
        //holder.tv_rank.setText(music.get(pos).getRank());
        if(music.get(pos).get_Like().compareTo("1")==0){
            holder.img_like.setImageResource(R.mipmap.icon_item_action_like_selected);
        }
        holder.img_like.setOnClickListener(img_listener);
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

        private ImageView img_music, img_like;
        private TextView tv_music_name, tv_artist_name;
        //private TextView tv_rank;
        public ViewHolder(View view){
            super(view);
            img_music = (ImageView) view.findViewById(R.id.img_music_cover_like);
            tv_music_name = (TextView) view.findViewById(R.id.text_music_name_like);
            tv_artist_name = (TextView) view.findViewById(R.id.text_artist_name_like);
            //tv_rank = (TextView) view.findViewById(R.id.text_music_rank);
            img_like = (ImageView) view.findViewById(R.id.img_music_like_like);
        }
    }

    public void postMusicLike(final int pos){
        Log.d("Music ID & Token : ", music.get(pos).get_id()+" "+MainActivity.token);
        MusicService service = network.buildRetrofit().create(MusicService.class);
        Call<Music> musicLikeCall = service.postLike(music.get(pos).get_id(), MainActivity.token);

        musicLikeCall.enqueue(new Callback<Music>() {
            @Override
            public void onResponse(Call<Music> call, Response<Music> response) {
                if(response.isSuccessful()){
                    Music tmp_music = response.body();
                    Log.d("Music Like Test : ", tmp_music.get_id()+" "+tmp_music.getTitle());
                    Toast.makeText(context, "좋아요가 반영되었습니다.", Toast.LENGTH_LONG).show();
                    music.get(pos).set_Like("1");
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
            public void onFailure(Call<Music> call, Throwable t) {
                Toast.makeText(context, "Failed to Like", Toast.LENGTH_LONG).show();
                Log.i("TEST","err : "+ t.getMessage());
            }
        });
    }
}
