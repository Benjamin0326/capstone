package com.android.capstone.yolo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by sung9 on 2017-04-19.
 */

public class FestivalAdapter extends RecyclerView.Adapter<FestivalAdapter.ViewHolder>{

    private int[] list_img_festival;
    private Context context;

    public FestivalAdapter(Context _context, int[] _list_img_festival){
        context = _context;
        list_img_festival = _list_img_festival;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_festival, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.img_festival.setImageResource(list_img_festival[position]);
        final int pos = position;
        ImageView.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int data = list_img_festival[pos];
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment fr = new FestivalInfoFragment();
                activity.getFragmentManager().beginTransaction().replace(R.id.container_fragment, fr).commit();

            }
        };

        holder.img_festival.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return list_img_festival.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView img_festival;
        public ViewHolder(View view){
            super(view);
            img_festival = (ImageView) view.findViewById(R.id.img_item_festival);
        }
    }
}
