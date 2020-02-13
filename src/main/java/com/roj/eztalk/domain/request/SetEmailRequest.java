package com.roj.eztalk.domain.request;

import lombok.Data;

@Data
public class SetEmailRequest {
    private Integer token;
    private String email;
}