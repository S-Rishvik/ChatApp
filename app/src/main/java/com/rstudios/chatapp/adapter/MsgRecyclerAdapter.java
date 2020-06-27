package com.rstudios.chatapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rstudios.chatapp.R;
import com.rstudios.chatapp.model.MsgItem;

import java.util.ArrayList;

public class MsgRecyclerAdapter  extends RecyclerView.Adapter<MsgRecyclerAdapter.MsgHolder>{

    private Context context;
    private ArrayList<MsgItem> arrayList;

    public MsgRecyclerAdapter(Context context, ArrayList<MsgItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MsgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_msg,parent,false);
        return new MsgHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MsgHolder holder, int position) {
        MsgItem msgItem=arrayList.get(position);
        holder.setHolder(msgItem);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MsgHolder extends RecyclerView.ViewHolder{
        TextView msg;

        public MsgHolder(@NonNull View itemView) {
            super(itemView);
            msg=itemView.findViewById(R.id.msg_text);
        }

        void setHolder(MsgItem msgItem){
            msg.setText(msgItem.getMsg());
        }
    }
}
