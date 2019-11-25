package com.roj.eztalk.data;


import lombok.Data;

@Data
public class Message {
    private String content;
    private User user;
    
    public Message() {}
    public Message(String content, User user) {
        this.user = user;
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
    public User getUser(){
        return this.user;
    }
}