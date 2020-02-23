package com.roj.eztalk.domain;

import java.util.Collections;
import java.util.List;

import lombok.Data;

@Data
public class ChatroomItem {
    private Long id;
    private String name;
    private String description;
    private String language;
    private String type;
    private String lastUpdated;

    public ChatroomItem(Chatroom chatroom){
        this.id = chatroom.getId();
        this.name = chatroom.getName();
        this.description = chatroom.getDescription();
        this.language = chatroom.getLanguage();
        this.type = chatroom.getType();
        List<Message> messages = chatroom.getMessages();
        if(messages.isEmpty()){
            this.lastUpdated = null;
        } else {
            Collections.sort(messages);
            this.lastUpdated = messages.get(messages.size() - 1).getDateTime();
        }
    }
}