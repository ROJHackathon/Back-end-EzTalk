package com.roj.eztalk.domain.request;

import lombok.Data;

@Data
public class SetTargetLanguageRequest {
    private Long token;
    private String targetLanguage;
}