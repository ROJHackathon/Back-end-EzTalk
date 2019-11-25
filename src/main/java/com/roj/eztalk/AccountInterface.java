package com.roj.eztalk;

import com.roj.eztalk.data.*;
import java.util.List;

interface AccountInterface {
    public List<User> getOnlineUser();
    public List<User> getAllUser();
    public int getToken(String name);
    public String getStatus(Token token);
    public LoginResponseBody login(String userName, String password);
    public LogOutResponseBody logout(Token token);
    public boolean isUserNameRegistered(String name);
    public boolean isValid(String name, String password);
    public boolean isOnline(Token token);
    public User signup(String userName, String password);
}