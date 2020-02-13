package com.roj.eztalk.domain.request;

import lombok.Data;

@Data
public class SetTargetLanguageRequest {
    private Integer token;
    private String targetLanguage;
}