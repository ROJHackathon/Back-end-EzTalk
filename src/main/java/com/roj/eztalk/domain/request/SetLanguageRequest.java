package com.roj.eztalk.domain.request;

import lombok.Data;

@Data
public class SetLanguageRequest {
    private Integer token;
    private String language;
}