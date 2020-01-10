package com.roj.eztalk.data.request;

import lombok.Data;

@Data
public class SetLanguageRequest {
    private Integer token;
    private String language;
}