package com.roj.eztalk.data;

import lombok.Data;

@Data
public class Comment {
    private Long id;
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