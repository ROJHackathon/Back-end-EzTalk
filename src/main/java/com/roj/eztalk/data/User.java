package com.roj.eztalk.data;

import lombok.Data;

@Data
public class User {
    //TODO: avatar url
    private int id;
    private String name;
    private String password;
    private String avatarUrl;
    public User() {}
    public User(int uid, String name, String avatarUrl) {
        this.id = uid;
        this.name = name;
        this.avatarUrl = avatarUrl;
    }
    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}