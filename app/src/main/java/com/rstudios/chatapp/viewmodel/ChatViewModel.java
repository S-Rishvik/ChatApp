package com.rstudios.chatapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rstudios.chatapp.model.MsgItem;
import com.rstudios.chatapp.repository.ChatRepository;

import java.util.ArrayList;

public class ChatViewModel extends ViewModel {
    public MutableLiveData<ArrayList<MsgItem>> mutableLiveData;
    private ChatRepository chatRepository;
    public MutableLiveData<ArrayList<MsgItem>> getMessages(String channel){
        if(chatRepository==null)
            chatRepository=new ChatRepository();
        if(mutableLiveData==null)
            mutableLiveData=chatRepository.fetchMessages(channel);
        return mutableLiveData;
    }
}
