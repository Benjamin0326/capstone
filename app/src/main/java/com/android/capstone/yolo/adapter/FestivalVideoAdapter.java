package com.android.capstone.yolo.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.layer.festival.GetFilePathFromDevice;
import com.google.android.youtube.player.YouTubePlayer;
import com.squareup.picasso.Picasso;

/**
 * Created by sung9 on 2017-04-26.
 */

public class FestivalVideoAdapter extends RecyclerView.Adapter<FestivalVideoAdapter.ViewHolder>{

    private String[] videoResources;
    private Uri[] videoUris;
    private Context context;
    public static final String API_KEY = "AIzaSyBz948TjD7Wo993-0TbSh3wMf5nqYGZCh0";

    public FestivalVideoAdapter(Context _context, Uri[] _videoUris, String[] _videoResources){
        context = _context;
        videoUris = _videoUris;
        videoResources = _videoResources;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_festival_video, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final int pos = position;
        ImageView.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(Intent.ACTION_VIEW,videoUris[pos]));
                //String data = dummyData[pos];
                //Toast.makeText(context, pos+" clicked", Toast.LENGTH_SHORT).show();
                /*Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_festival_item_video);
                dialog.setTitle(pos+" 번째 동영상");

                TextView textView = (TextView) dialog.findViewById(R.id.text_dialog_festival_video);
                textView.setText(pos+"번째 영상입니다.");

                VideoView videoView = (VideoView) dialog.findViewById(R.id.video_dialog_festival_video);

                final MediaController mediaController =
                        new MediaController(context);
                videoView.setMediaController(mediaController);

                dialog.show();
                videoView.start();*/
            }
        };
        //Picasso.with(context).load(videoResources[pos]).into(holder.img_pic);
        //String selectedVideoFilePath = GetFilePathFromDevice.getPath(context, videoResources[pos]);
        //holder.img_pic.setImageBitmap(ThumbnailUtils.createVideoThumbnail(selectedVideoFilePath, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND));
        //Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(selectedVideoFilePath, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
        holder.img_pic.setImageResource(R.drawable.festivalinfo);
        //Picasso.with(context).load(videoResources[i]).into(holder.img_pic);
        holder.img_pic.setOnClickListener(listener);
    }
    //startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com/watch?v=Hxy8BZGQ5Jo")));
    @Override
    public int getItemCount() {
        return videoResources.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView img_pic;
        public ViewHolder(View view){
            super(view);
            img_pic = (ImageView) view.findViewById(R.id.youtube_item_festival_video);

        }

    }
}