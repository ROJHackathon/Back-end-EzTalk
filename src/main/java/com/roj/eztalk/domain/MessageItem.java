package com.roj.eztalk.domain;

import lombok.Data;

@Data
public class MessageItem {
    private Long id;
    private String content;
    private UserItem author;

    public MessageItem(Message message){
        this.id = message.getId();
        this.content = message.getContent();
        this.author = new UserItem(message.getAuthor());
    }
}