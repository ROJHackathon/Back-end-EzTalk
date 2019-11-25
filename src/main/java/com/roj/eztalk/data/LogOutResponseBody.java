package com.roj.eztalk.data;

import lombok.Data;

@Data
public class LogOutResponseBody {
    private String message;
    
    public LogOutResponseBody() {}
    public LogOutResponseBody(String message) {
        this.message = message;
    }
}