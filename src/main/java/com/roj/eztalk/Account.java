package com.roj.eztalk;

import java.time.LocalDateTime;
import com.roj.eztalk.data.*;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class Account implements AccountInterface {
    private static HashMap<Integer, User> tokenMap = new HashMap<>();
    private static HashMap<Integer, User> userMap = new HashMap<>();
    static {
        User wong = new User(0, "wcl", "http://placeimg.com/80/80/people/3");
        wong.setPassword("wong1215");
        userMap.put(0, wong);
    }
    @Override
    public int getToken(String name){
        String dateTime = LocalDateTime.now().toString();
        String userName = name;
        String salt = "gewong";
        return String.format("%s:%s:%s", salt, userName, dateTime).hashCode();
    }
    @Override
    public String getStatus(Token token){
        return tokenMap.containsKey(token.getToken()) ? "online" : "offline";
    }
    @Override
    public Integer login(String userName, String password){
        if(!isUserNameRegistered(userName)){
            return 0;
        }
        if(!isValid(userName, password)){
            return -1;
        }
        int token = getToken(userName);
        return token;
    }
    @Override
    public void logout(Token token){
        tokenMap.remove(token.getToken());
    }
    @Override
    public boolean isUserNameRegistered(String name){
        int uid = name.hashCode();
        return userMap.containsKey(uid);
    }
    @Override
    public boolean isValid(String name, String password){
        int uid = name.hashCode();
        return password.equals(userMap.get(uid).getPassword());
    }
    @Override
    public boolean isOnline(Token token){
        return tokenMap.containsKey(token.getToken());
    }
    @Override
    public String signup(String userName, String password){
        if(isUserNameRegistered(userName)){
            return "user named already registerd";
        }
        int uid = userName.hashCode();
        User newUser = new User(uid, userName, password);
        userMap.put(uid, newUser);
        return "success";
    }
}