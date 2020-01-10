package com.roj.eztalk.data.request;

import lombok.Data;

@Data
public class SetPreferenceRequest {
    private Integer token;
    private String preference;
}