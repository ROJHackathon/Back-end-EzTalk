package com.roj.eztalk.service;

import java.util.Optional;

import com.roj.eztalk.domain.User;
import com.roj.eztalk.domain.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    // for retrieving and modifying user information
    @Autowired
    private UserRepository userRepository;

    public boolean isValidNameAndPasswordValid(String name, String password) {
        Optional<User> user = userRepository.findByName(name);
        if(!user.isPresent()) return false;
        return (user.get().getPassword().equals(password));
    }
    /**
     * register the given name and password
     * @param name
     * @param password
     * @return the registered User or null if name is not available
     */
    public User register(String name, String password) {
        Optional<User> user = userRepository.findByName(name);
        if(user.isPresent()) return null;
        User ret = userRepository.save(new User(name, password));
        return ret;
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