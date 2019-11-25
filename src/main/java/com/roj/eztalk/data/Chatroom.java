package com.roj.eztalk.data;

import lombok.Data;

@Data
public class Chatroom {
    private Long id;
    private String name;
    private String language;
    
    public Chatroom() {}
    public Chatroom(String name, String language) {
        this.name = name;
        this.language = language;
    }
    public Long getId(){
        return this.id;
    }
}