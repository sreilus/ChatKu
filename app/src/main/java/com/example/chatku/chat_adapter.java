package com.example.chatku;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class chat_adapter extends RecyclerView.Adapter<chat_adapter.ViewHolder> {
    ArrayList<String> chat_data=new ArrayList<String>();
    ArrayList<String> pengirim_=new ArrayList<String>();

    public chat_adapter(ArrayList<String> chat_data, ArrayList<String> pengirim_) {
        this.chat_data=chat_data;
        this.pengirim_=pengirim_;
    }

    @NonNull
    @Override
    public chat_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull chat_adapter.ViewHolder holder, int position) {
        if (pengirim_.get(position)=="me")
        {
            holder.getText_chat().setText(chat_data.get(position));
            holder.getUser().setText(pengirim_.get(position));
            holder.getText_chat().setBackground(holder.itemView.getContext().getResources().getDrawable(R.drawable.bg_me));
            holder.getLayout().setGravity(Gravity.RIGHT);
        }
        else
        {
            holder.getText_chat().setText(chat_data.get(position));
            holder.getUser().setText("Diğer Kullanıcı");
            holder.getText_chat().setBackground(holder.itemView.getContext().getResources().getDrawable(R.drawable.bg_you));
            holder.getLayout().setGravity(Gravity.LEFT);
        }
    }

    @Override
    public int getItemCount() {
        return chat_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text_chat,user;
        LinearLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            text_chat=itemView.findViewById(R.id.text_chat);
            user=itemView.findViewById(R.id.user);
            layout=itemView.findViewById(R.id.layout);
        }

        public TextView getText_chat() {
            return text_chat;
        }

        public TextView getUser() {
            return user;
        }

        public LinearLayout getLayout() {
            return layout;
        }
    }
}
