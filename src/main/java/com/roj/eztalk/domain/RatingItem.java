package com.roj.eztalk.domain;

import lombok.Data;

@Data
public class RatingItem {
    private Long id;
    private Integer rating;
    private UserItem author;
    public RatingItem(Rating rating) {
        id = rating.getId();
        this.rating = rating.getRating();
        this.author = new UserItem(rating.getAuthor());
    }
}