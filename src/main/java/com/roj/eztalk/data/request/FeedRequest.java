package com.roj.eztalk.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeedRequest {
    private Integer token;
    private Integer page;
}