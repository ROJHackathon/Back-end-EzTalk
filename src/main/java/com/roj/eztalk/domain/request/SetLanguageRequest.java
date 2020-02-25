package com.roj.eztalk.domain.request;

import lombok.Data;

@Data
public class SetLanguageRequest {
    private Long token;
    private String language;
}