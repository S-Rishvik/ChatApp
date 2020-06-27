package com.rstudios.chatapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rstudios.chatapp.Contractor;
import com.rstudios.chatapp.R;
import com.rstudios.chatapp.model.ChatItem;
import com.rstudios.chatapp.view.ChatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.ChatHolder> implements Filterable {
    private Context context;
    private ArrayList<ChatItem> arrayList;
    private ArrayList<ChatItem> copyOfList;
    private Activity activity;

    public ChatRecyclerAdapter(Context context, ArrayList<ChatItem> arrayList, Activity activity) {
        this.context = context;
        this.arrayList = arrayList;
        copyOfList=new ArrayList<>(arrayList);
        this.activity = activity;
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_chat,parent,false);
        return new ChatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        ChatItem chatItem=arrayList.get(position);
        holder.setHolder(chatItem);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ChatActivity.class);
                intent.putExtra(Contractor.CHANNEL,chatItem.getChannel());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ChatHolder extends RecyclerView.ViewHolder{
        TextView channel,msg;
        View view;
        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            channel=itemView.findViewById(R.id.chat_channel);
            msg=itemView.findViewById(R.id.chat_msg);
            view=itemView;
        }
        void setHolder(ChatItem chatItem){
            channel.setText(chatItem.getChannel());
            msg.setText(chatItem.getMsg());
        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ChatItem> filteredList=new ArrayList<>();
            if(charSequence==null||charSequence.length()==0){
                filteredList.addAll(copyOfList);
            }
            else {
                String filterPattern=charSequence.toString().toLowerCase().trim();
                for(ChatItem chatItem : copyOfList){
                    if(chatItem.getChannel().toLowerCase().contains(filterPattern)){
                        filteredList.add(chatItem);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            arrayList.clear();
            arrayList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
