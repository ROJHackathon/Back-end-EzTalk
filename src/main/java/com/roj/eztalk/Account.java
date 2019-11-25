package com.roj.eztalk;

import java.time.LocalDateTime;
import com.roj.eztalk.data.*;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class Account implements AccountInterface {
    private static HashMap<Integer, User> tokenMap = new HashMap<>();
    private static HashMap<Integer, User> userMap = new HashMap<>();
    static {
    }

    public List<User> getOnlineUser(){
        List<User> ret = new ArrayList<User>(tokenMap.values());
        return ret;
    }
    
    public List<User> getAllUser() {
        List<User> ret = new ArrayList<User>(userMap.values());
        return ret;
    }

    @Override
    public int getToken(String name) {
        String dateTime = LocalDateTime.now().toString();
        String userName = name;
        String salt = "gewong";
        Integer token = String.format("%s:%s:%s", salt, userName, dateTime).hashCode();
        if (tokenMap.containsKey(token)) {
            token++;
        }
        return token;
    }

    @Override
    public String getStatus(Token token) {
        return tokenMap.containsKey(token.getToken()) ? "online" : "offline";
    }

    @Override
    public LoginResponseBody login(String userName, String password) {
        if (!isUserNameRegistered(userName)) {
            return new LoginResponseBody("User Name Does Not Exist", null);
        }
        if (!isValid(userName, password)) {
            return new LoginResponseBody("Invalid Password", null);
        }
        int token = getToken(userName);
        Integer uid = nameToId(userName);
        User user = userMap.get(uid);
        tokenMap.put(token, user);
        return new LoginResponseBody("Login success", token);
    }

    @Override
    public LogOutResponseBody logout(Token token) {
        if(!tokenMap.containsKey(token.getToken())){
            return new LogOutResponseBody("Invalid token");
        }
        tokenMap.remove(token.getToken());
        return new LogOutResponseBody("Logged out");
    }

    @Override
    public boolean isUserNameRegistered(String name) {
        Integer uid = this.nameToId(name);
        if (uid == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isValid(String name, String password) {
        Integer uid = this.nameToId(name);
        if (uid == null) {
            return false;
        }
        if (!userMap.get(uid).getPassword().equals(password)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isOnline(Token token) {
        return tokenMap.containsKey(token.getToken());
    }

    @Override
    public User signup(String userName, String password) {
        if (isUserNameRegistered(userName)) {
            return new User(null, null, null);
        }
        int uid = userName.hashCode();
        while (userMap.containsKey(uid)) {
            uid++;
        }
        User newUser = new User(uid, userName, password);
        userMap.put(uid, newUser);
        return newUser;
    }

    public Integer nameToId(String name) {
        int uid = name.hashCode();
        while (userMap.containsKey(uid)) {
            if (userMap.get(uid).getName().equals(name)) {
                return uid;
            }
            uid++;
        }
        return null;
    }

    public User getUser(Integer id) {
        return userMap.get(id);
    }
}