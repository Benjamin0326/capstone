package com.android.capstone.yolo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.capstone.yolo.R;
import com.android.capstone.yolo.model.BoardList;

import java.util.List;

class BoardViewHolder extends RecyclerView.ViewHolder{
    TextView title, type, writer, date;

    public BoardViewHolder(View view){
        super(view);

        title = (TextView) view.findViewById(R.id.board_title);
        type = (TextView) view.findViewById(R.id.board_type);
        writer = (TextView) view.findViewById(R.id.board_writer);
        date = (TextView) view.findViewById(R.id.board_date);
    }
}

public class BoardSearchResultAdapter extends RecyclerView.Adapter<BoardViewHolder> implements AdapterView.OnItemClickListener{
    Context context;
    List<BoardList> lists;

    public BoardSearchResultAdapter(Context context){
        this.context = context;
    }

    public void setSource(List<BoardList> list) {
        this.lists = list;
        this.notifyDataSetChanged();
    }

    @Override
    public BoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board_list, parent, false);
        return new BoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BoardViewHolder holder, int position) {
        /*
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

                if(response.code() >= 500) {
                    Toast.makeText(context, "Server err " + response.code() + " : " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileImage> call, Throwable t) {
                Log.d("TEST", "err : " + t.getMessage());
            }
        });
        */
        holder.date.setText(lists.get(position).getDate());
        holder.title.setText(lists.get(position).getTitle());
        holder.writer.setText(lists.get(position).getUser());
        holder.type.setText(lists.get(position).getType());
    }

    @Override
    public int getItemCount() {
        return lists != null ? lists.size() : 0;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}