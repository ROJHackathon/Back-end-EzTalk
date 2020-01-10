package com.roj.eztalk.data.request;

import lombok.Data;

@Data
public class SetEmailRequest {
    private Integer token;
    private String email;
}