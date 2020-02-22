package com.roj.eztalk.domain.request;

import lombok.Data;

@Data
public class SetEmailRequest {
    private Long token;
    private String email;
}