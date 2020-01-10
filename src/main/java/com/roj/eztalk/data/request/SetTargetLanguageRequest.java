package com.roj.eztalk.data.request;

import lombok.Data;

@Data
public class SetTargetLanguageRequest {
    private Integer token;
    private String targetLanguage;
}