package com.rstudios.chatapp.model;


import com.google.firebase.Timestamp;

public class ChatItem {
    private String channel,msg;
    public ChatItem(String channel, String msg) {
        this.channel = channel;
        this.msg = msg;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
