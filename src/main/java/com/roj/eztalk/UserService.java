package com.roj.eztalk;

import java.util.Optional;

import com.roj.eztalk.data.User;
import com.roj.eztalk.data.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserInterface {
    @Autowired
    private UserRepository userRepository;

    public boolean isNameRegistered(String name) {
        Optional<User> user = userRepository.findByName(name);
        return user.isPresent();
    }

    public boolean isValidNameAndPasswordValid(String name, String password) {
        Optional<User> user = userRepository.findByName(name);
        if(!user.isPresent()) return false;
        return (user.get().getPassword().equals(password));
    }

    public boolean register(String name, String password) {
        if(isNameRegistered(name)) return false;
        User user = new User(name, password);
        userRepository.save(user);
        return true;
    }
}