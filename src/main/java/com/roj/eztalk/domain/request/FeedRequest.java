package com.roj.eztalk.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedRequest {
    private Long token;
    private Integer page;
}