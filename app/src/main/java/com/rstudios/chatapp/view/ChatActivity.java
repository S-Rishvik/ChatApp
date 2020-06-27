package com.rstudios.chatapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rstudios.chatapp.Contractor;
import com.rstudios.chatapp.R;
import com.rstudios.chatapp.adapter.MsgRecyclerAdapter;
import com.rstudios.chatapp.model.MsgItem;
import com.rstudios.chatapp.viewmodel.ChatViewModel;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {
    @BindView(R.id.progressBar2) ProgressBar progressBar;
    @BindView(R.id.chat_text) EditText chatText;
    @BindView(R.id.chat_send) ImageView send;
    @BindView(R.id.chat_recyclerView) RecyclerView recyclerView;
    @BindView(R.id.chat_toolbar)
    Toolbar toolbar;
    ChatViewModel chatViewModel;
    DatabaseReference channelReference,baseReference;
    String channel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        chatViewModel=new ViewModelProvider(this).get(ChatViewModel.class);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        channel=getIntent().getStringExtra(Contractor.CHANNEL);
        toolbar.setTitle(channel);
        channelReference=FirebaseDatabase.getInstance().getReference().child(Contractor.MSG).child(channel);
        baseReference=FirebaseDatabase.getInstance().getReference().child(Contractor.CHANNEL).child(channel);
        chatViewModel.getMessages(channel).observe(this, new Observer<ArrayList<MsgItem>>() {
            @Override
            public void onChanged(ArrayList<MsgItem> msgItems) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(new MsgRecyclerAdapter(getApplicationContext(),msgItems));
            }
        });
    }

    @OnClick(R.id.chat_send)void setSend(View view){
        String text=chatText.getText().toString().trim();
        if("".equals(text)||text==null){
            Toast.makeText(getApplicationContext(),"Enter text to send",Toast.LENGTH_SHORT).show();
            return;
        }
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put(Contractor.MSG,text);
        hashMap.put(Contractor.TIMESTAMP, Timestamp.now());
        hashMap.put(Contractor.CHANNEL,channel);
        channelReference.push().setValue(hashMap);
        baseReference.setValue(hashMap);
        chatText.setText("");

    }

}