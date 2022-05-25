package com.myapp.chat.ClinetServer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.myapp.chat.R;

import java.util.List;


public class ClinetAdapter extends RecyclerView.Adapter<ClinetAdapter.MyViewHolder> {
    private List<MessageAdapter> messageAdapterList;

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nickname;
        public TextView message;


        public MyViewHolder(View view) {
            super(view);

            nickname = (TextView) view.findViewById(R.id.nickname);
            message = (TextView) view.findViewById(R.id.message);





        }
    }
    public ClinetAdapter(List<MessageAdapter> messagesList) {

        this.messageAdapterList = messagesList;


    }

    @Override
    public int getItemCount() {
        return messageAdapterList.size();
    }
    @Override
    public ClinetAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);



        return new ClinetAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ClinetAdapter.MyViewHolder holder, final int position) {
        final MessageAdapter m = messageAdapterList.get(position);
        holder.nickname.setText(m.getNickname() +" : ");

        holder.message.setText(m.getMessage() );




    }



}