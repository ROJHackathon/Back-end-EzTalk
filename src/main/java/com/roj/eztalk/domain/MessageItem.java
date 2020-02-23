package com.roj.eztalk.domain;

import lombok.Data;

@Data
public class MessageItem {
    private Long id;
    private String content;
    private String dateTime;
    private UserItem author;

    public MessageItem(Message message){
        this.id = message.getId();
        this.content = message.getContent();
        this.dateTime = message.getDateTime();
        this.author = new UserItem(message.getAuthor());
    }
}