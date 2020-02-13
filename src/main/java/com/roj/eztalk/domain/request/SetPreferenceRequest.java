package com.roj.eztalk.domain.request;

import lombok.Data;

@Data
public class SetPreferenceRequest {
    private Integer token;
    private String preference;
}