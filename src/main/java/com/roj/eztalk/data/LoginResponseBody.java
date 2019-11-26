package com.roj.eztalk.data;

import lombok.Data;

@Data
public class LoginResponseBody {
    private String message;
    private Integer token;
    private String userName;
    public LoginResponseBody() {}
    public LoginResponseBody(String message, Integer token, String userName) {
        this.message = message;
        this.token = token;
        this.userName = userName;
    }
}