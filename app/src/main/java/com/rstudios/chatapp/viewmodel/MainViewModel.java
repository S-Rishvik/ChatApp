package com.rstudios.chatapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rstudios.chatapp.model.ChatItem;
import com.rstudios.chatapp.repository.MainRepository;

import java.util.ArrayList;

import static com.rstudios.chatapp.Contractor.*;

public class MainViewModel extends ViewModel {
    public MutableLiveData<ArrayList<ChatItem>> listMutableLiveData;
    private MainRepository mainRepository;

    public MutableLiveData<ArrayList<ChatItem>> getChannels(){
        if(mainRepository==null)
            mainRepository=MainRepository.getInstance();
        if(listMutableLiveData==null)
            listMutableLiveData=mainRepository.fetchChannels();
        return listMutableLiveData;
    }
}
