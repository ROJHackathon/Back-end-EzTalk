package com.roj.eztalk.data;

import lombok.Data;

@Data
public class LoginResponseBody {
    private String message;
    private Integer token;
    
    public LoginResponseBody() {}
    public LoginResponseBody(String message, Integer token) {
        this.message = message;
        this.token = token;
    }
}