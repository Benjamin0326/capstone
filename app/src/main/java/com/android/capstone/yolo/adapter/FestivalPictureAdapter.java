package com.android.capstone.yolo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.layer.festival.GalleryActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by sung9 on 2017-04-26.
 */

public class FestivalPictureAdapter extends RecyclerView.Adapter<FestivalPictureAdapter.ViewHolder>{

    private String[] imageResources;
    private Context context;

    public FestivalPictureAdapter(Context _context, String[] _imageResources){
        context = _context;
        imageResources = _imageResources;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_festival_picture, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final int pos = position;
        ImageView.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String data = dummyData[pos];
                //Toast.makeText(context, pos+" clicked", Toast.LENGTH_SHORT).show();
                /*
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_festival_item_picture);
                dialog.setTitle((pos+1)+" 번째 사진");

                //TextView textView = (TextView) dialog.findViewById(R.id.text_dialog_festival_picture);
                //textView.setText(pos+"번째 사진입니다.");

                ImageView imageView = (ImageView) dialog.findViewById(R.id.img_dialog_festival_picture);
                Picasso.with(context).load(imageResources[pos+2]).into(imageView);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                Window window = dialog.getWindow();
                lp.copyFrom(window.getAttributes());
//This makes the dialog take up the full width
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.onWindowAttributesChanged(lp);
                window.setAttributes(lp);
                dialog.show();
                */
                Intent intent = new Intent(context, GalleryActivity.class);
                intent.putExtra("gallery", imageResources[pos+2]);
                context.startActivity(intent);
            }
        };
        Picasso.with(context).load(imageResources[pos+2]).resize(500,500).centerCrop().into(holder.img_pic);
        holder.img_pic.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return imageResources.length-2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView img_pic;
        public ViewHolder(View view){
            super(view);
            img_pic = (ImageView) view.findViewById(R.id.image_item_festival_picture);

        }

    }
}