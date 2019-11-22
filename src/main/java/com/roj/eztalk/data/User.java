package com.roj.eztalk.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
public class User {
    private @Id @GeneratedValue Long id;
    private String name;

    public User(String name) {
        this.name = name;
    }
}