package com.roj.eztalk;

public interface UserInterface {
    public boolean isNameRegistered(String name);
    public boolean isValidNameAndPasswordValid(String name, String password);
    public boolean register(String name, String password);
}