package com.roj.eztalk;

import java.util.List;

import com.roj.eztalk.data.*;
import com.roj.eztalk.data.response.*;

interface SessionInterface {
    public int getToken(String name);
    public String getStatus(Integer token);
    public LoginResponse login(String userName, String password);
    public LogoutResponse logout(Integer token);
    public boolean isOnline(Integer token);
    public boolean isTokenValid(Integer token);
    public List<User> getOnlineUsers();
}