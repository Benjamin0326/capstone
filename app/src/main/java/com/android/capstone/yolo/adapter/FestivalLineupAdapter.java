package com.android.capstone.yolo.adapter;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.capstone.yolo.R;
import com.squareup.picasso.Picasso;

/**
 * Created by sung9 on 2017-05-01.
 */

public class FestivalLineupAdapter  extends RecyclerView.Adapter<FestivalLineupAdapter.ViewHolder>{

    private String[] lineup;
    private Context context;

    public FestivalLineupAdapter(Context _context, String[] _lineup){
        context = _context;
        lineup = _lineup;
    }

    @Override
    public FestivalLineupAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_festival_lineup, parent, false);
        FestivalLineupAdapter.ViewHolder holder = new FestivalLineupAdapter.ViewHolder(v);
        return holder;
    }


    @Override
    public void onBindViewHolder(FestivalLineupAdapter.ViewHolder holder, int position) {

        final int pos = position;
        holder.tv_lineup.setText(lineup[pos]);
        TextView.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String data = dummyData[pos];
                //Toast.makeText(context, pos+" clicked", Toast.LENGTH_SHORT).show();
                /*Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_festival_item_picture);
                dialog.setTitle(pos+" 번째 사진");

                TextView textView = (TextView) dialog.findViewById(R.id.text_dialog_festival_picture);
                textView.setText(pos+"번째 사진입니다.");

                ImageView imageView = (ImageView) dialog.findViewById(R.id.img_dialog_festival_picture);
                Picasso.with(context).load(imageResources[pos]).into(imageView);

                dialog.show();
                */
            }
        };
        holder.tv_lineup.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return lineup.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_lineup;
        public ViewHolder(View view){
            super(view);
            tv_lineup = (TextView) view.findViewById(R.id.text_item_festival_lineup);

        }

    }
}
