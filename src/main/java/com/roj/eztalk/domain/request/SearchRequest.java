package com.roj.eztalk.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchRequest {
    private String text;
    private Integer token;
}