package com.roj.eztalk.data;

import lombok.Data;

@Data
public class Chatroom {
    private Integer id;
    private String name;
    private String language;
    
    public Chatroom() {}
    public Chatroom(Integer id, String name, String language) {
        this.id = id;
        this.name = name;
        this.language = language;
    }
    public Integer getId(){
        return this.id;
    }
    public String getName() { 
        return this.name;
    }
    public String getLanguage() {
        return this.language;
    }
}