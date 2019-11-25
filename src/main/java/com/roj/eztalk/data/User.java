package com.roj.eztalk.data;

import lombok.Data;

@Data
public class User {
    // TODO: avatar url
    private Integer id;
    private String name, password;
    private String avatarUrl = null;
    private String email = null;
    private String language = null;
    private String preference = null;

    public User() {
    }

    public User(Integer uid, String name, String password) {
        this.id = uid;
        this.name = name;
        this.password = password;
    }

    public User(Integer uid, String name, String password, String avatarUrl) {
        this.id = uid;
        this.name = name;
        this.password = password;
        this.avatarUrl = avatarUrl;
    }

    public User(Integer uid, String name, String avatarUrl, String email, String language, String preference) {
        this.id = uid;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.email = email;
        this.language = language;
        this.preference = preference;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPreference(String p) {
        this.preference = p;
    }

    public String getPreference() {
        return this.preference;
    }
}