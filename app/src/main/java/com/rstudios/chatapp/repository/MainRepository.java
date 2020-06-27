package com.rstudios.chatapp.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.rstudios.chatapp.model.ChatItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;

import static com.rstudios.chatapp.Contractor.*;

public class MainRepository {
    private static MainRepository instance;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(CHANNEL);
    public MutableLiveData<ArrayList<ChatItem>> listMutableLiveData=new MutableLiveData<>();
    public static MainRepository getInstance(){
        if(instance==null)
            instance=new MainRepository();
        return instance;
    }

    public MutableLiveData<ArrayList<ChatItem>> fetchChannels(){
        databaseReference.orderByChild(TIMESTAMP+"/seconds").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ChatItem> arrayList=new ArrayList<>();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Log.i("snapshot",dataSnapshot.child(CHANNEL).getValue(String.class));
                    arrayList.add(new ChatItem(dataSnapshot.child(CHANNEL).getValue(String.class)+"", dataSnapshot.child(MSG).getValue(String.class)+""));
                }
                Collections.reverse(arrayList);
                listMutableLiveData.postValue(arrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return listMutableLiveData;
    }
}
