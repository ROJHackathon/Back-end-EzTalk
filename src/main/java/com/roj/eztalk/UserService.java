package com.roj.eztalk;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.roj.eztalk.data.User;
import com.roj.eztalk.data.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private Map<Long, String> idNameMap = new HashMap<Long, String>();

    public boolean isNameRegistered(String name) {
        return idNameMap.values().contains(name);
    }

    public boolean isValidNameAndPasswordValid(String name, String password) {
        if(!isNameRegistered(name)) return false;
        Optional<User> user = userRepository.findByName(name);
        if(!user.isPresent()) return false;
        return (user.get().getPassword().equals(password));
    }

    public User register(String name, String password) {
        if(isNameRegistered(name)) return null;
        User user = userRepository.save(new User(name, password));
        return user;
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public User setPreference(Long id, String preference) {
        Optional<User> opUser = userRepository.findById(id);
        if(!opUser.isPresent()) return null;
        User user = opUser.get();
        user.setPreference(preference);
        return userRepository.save(user);
    }

    public User setEmail(Long id, String email){
        Optional<User> opUser = userRepository.findById(id);
        if(!opUser.isPresent()) return null;
        User user = opUser.get();
        user.setEmail(email);
        return userRepository.save(user);
    }

    public User setLanguage(Long id, String language) {
        Optional<User> opUser = userRepository.findById(id);
        if(!opUser.isPresent()) return null;
        User user = opUser.get();
        user.setLanguage(language);
        return userRepository.save(user);
    }

    public User setTargetLanguage(Long id, String language) {
        Optional<User> opUser = userRepository.findById(id);
        if(!opUser.isPresent()) return null;
        User user = opUser.get();
        user.setTargetLanguage(language);
        return userRepository.save(user);
    }
}