package com.roj.eztalk.data;

import lombok.Data;

@Data
public class Token {
    private Integer token;

    public Token() {}
    public Token(int token) {
        this.token = token;
    }
    public Integer getToken(){
        return this.token;
    }
}