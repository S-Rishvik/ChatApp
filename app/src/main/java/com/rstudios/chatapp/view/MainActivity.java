package com.rstudios.chatapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.database.FirebaseDatabase;
import com.rstudios.chatapp.R;
import com.rstudios.chatapp.adapter.ChatRecyclerAdapter;
import com.rstudios.chatapp.model.ChatItem;
import com.rstudios.chatapp.viewmodel.MainViewModel;
import static com.rstudios.chatapp.Contractor.*;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    MainViewModel viewModel;
    RecyclerView recyclerView;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.chat_toolbar) Toolbar toolbar;
    ChatRecyclerAdapter chatRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.main_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        chatRecyclerAdapter=new ChatRecyclerAdapter(getApplicationContext(),new ArrayList<>(),this);
        viewModel=new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.getChannels().observe(this, new Observer<ArrayList<ChatItem>>() {
            @Override
            public void onChanged(ArrayList<ChatItem> chatItems) {
                progressBar.setVisibility(View.GONE);
                chatRecyclerAdapter=new ChatRecyclerAdapter(getApplicationContext(),chatItems,MainActivity.this);
                recyclerView.setAdapter(chatRecyclerAdapter);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        MenuItem searchItem=menu.findItem(R.id.action_search);
        SearchView searchView= (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                chatRecyclerAdapter.getFilter().filter(s);
                return false;
            }
        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused) {
                if(!queryTextFocused) {
                    searchView.setQuery("", false);
                }
            }
        });
        return true;
    }

}