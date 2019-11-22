package com.roj.eztalk.data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
public class User {
    private @Id @GeneratedValue Long id;
    private String name;
    public User() {}
    public User(String name) {
        this.name = name;
    }
    public Long getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
}