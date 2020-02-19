package com.roj.eztalk.domain;

import lombok.Data;

@Data
public class CommentItem {
    private Long id;
    private String content;
    private UserItem author;

    public CommentItem(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.author = new UserItem(comment.getAuthor());
    }
}