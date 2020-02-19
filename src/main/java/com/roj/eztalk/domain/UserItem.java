package com.roj.eztalk.domain;

import lombok.Data;

@Data
public class UserItem {
    private Long id;
    private String name;
    private String avatarUrl;
    private String language;
    private String targetLanguage;
    private String preference;

    public UserItem(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.avatarUrl = user.getAvatarUrl();
        this.language = user.getLanguage();
        this.targetLanguage = user.getTargetLanguage();
        this.preference = user.getPreference();
    }
}