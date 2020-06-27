package com.rstudios.chatapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    public MutableLiveData<FirebaseUser> mutableLiveData=new MutableLiveData<>(firebaseAuth.getCurrentUser());
    public void setData(FirebaseUser firebaseUser){
        mutableLiveData.setValue(firebaseUser);
    }
}
