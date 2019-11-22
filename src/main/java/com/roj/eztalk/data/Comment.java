package com.roj.eztalk.data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
public class Comment {
    private @Id @GeneratedValue Long id;
    private String content;
    private User user;
    
    public Comment() {}
    public Comment(String content, User user) {
        this.user = user;
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
    public User getUser(){
        return this.user;
    }
    public Long getId(){
        return this.id;
    }
}