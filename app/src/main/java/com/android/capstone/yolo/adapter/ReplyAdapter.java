package com.android.capstone.yolo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.capstone.yolo.MainActivity;
import com.android.capstone.yolo.R;
import com.android.capstone.yolo.component.network;
import com.android.capstone.yolo.layer.profile.UserProfileActivity;
import com.android.capstone.yolo.model.ProfileImage;
import com.android.capstone.yolo.model.Reply;
import com.android.capstone.yolo.service.ProfileService;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ReplyViewHolder extends RecyclerView.ViewHolder{
    TextView writer, date, content;
    CircleImageView profile;

    public ReplyViewHolder(View view){
        super(view);

        writer = (TextView) view.findViewById(R.id.reply_writer);
        date = (TextView) view.findViewById(R.id.reply_date);
        content = (TextView) view.findViewById(R.id.reply_content);
        profile = (CircleImageView) view.findViewById(R.id.reply_profile);
    }
}

public class ReplyAdapter extends RecyclerView.Adapter<ReplyViewHolder>{
    Context context;
    List<Reply> lists;

    public ReplyAdapter(Context context){
        this.context = context;
    }

    public void setSource(List<Reply> list) {
        this.lists = list;
        this.notifyDataSetChanged();
    }

    @Override
    public ReplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reply, parent, false);
        return new ReplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReplyViewHolder holder, final int position) {
        CircleImageView.OnClickListener img_listener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserProfileActivity.class);
                intent.putExtra("userId", lists.get(position).getUser());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        };
        holder.profile.setOnClickListener(img_listener);

        ProfileService service = network.buildRetrofit().create(ProfileService.class);
        Call<ProfileImage> call = service.getProfile(lists.get(position).getUser(), MainActivity.token);
        call.enqueue(new Callback<ProfileImage>() {
            @Override
            public void onResponse(Call<ProfileImage> call, Response<ProfileImage> response) {
                if(response.isSuccessful()){
                    holder.writer.setText(response.body().getName());
                    if(!response.body().getImage().equals("profile/profile.jpg"))
                        Picasso.with(context).load(response.body().getImage()).into(holder.profile);

                    return;
                }
                Toast.makeText(context, "err " + response.code() + " : " + response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ProfileImage> call, Throwable t) {
                Log.d("TEST", "err : " + t.getMessage());
            }
        });
        holder.date.setText(lists.get(position).getDate());
        holder.content.setText(lists.get(position).getContent());
    }

    @Override
    public long getItemId(int position) { return 0; }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }
}
