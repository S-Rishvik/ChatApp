package com.rstudios.chatapp.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rstudios.chatapp.Contractor;
import com.rstudios.chatapp.model.MsgItem;

import java.util.ArrayList;
import java.util.Collections;

public class ChatRepository {

    public MutableLiveData<ArrayList<MsgItem>> mutableLiveData=new MutableLiveData<>();
    DatabaseReference channelReference= FirebaseDatabase.getInstance().getReference().child(Contractor.MSG);
    public MutableLiveData<ArrayList<MsgItem>> fetchMessages(String channel){
        channelReference.child(channel).limitToLast(50).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<MsgItem> arrayList=new ArrayList<>();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                    arrayList.add(new MsgItem(dataSnapshot.child(Contractor.MSG).getValue(String.class)));
                Collections.reverse(arrayList);
                mutableLiveData.postValue(arrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return mutableLiveData;
    }

}
