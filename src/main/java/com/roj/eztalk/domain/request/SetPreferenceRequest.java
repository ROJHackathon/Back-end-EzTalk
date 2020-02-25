package com.roj.eztalk.domain.request;

import lombok.Data;

@Data
public class SetPreferenceRequest {
    private Long token;
    private String preference;
}