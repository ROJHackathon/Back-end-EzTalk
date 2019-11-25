package com.roj.eztalk.data;

import lombok.Data;

@Data
public class LoginRequestBody {
    private String userName, password;
    
    public LoginRequestBody() {}
    public LoginRequestBody(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    public String getUserName(){
        return this.userName;
    }
    public String getPassword(){
        return this.password;
    }
}