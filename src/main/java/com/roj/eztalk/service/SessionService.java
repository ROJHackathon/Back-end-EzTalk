package com.roj.eztalk.service;

import com.roj.eztalk.domain.*;
import com.roj.eztalk.dao.*;
import com.roj.eztalk.domain.response.LoginResponse;
import com.roj.eztalk.domain.response.LogoutResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionService {
    // for log in, log out, token generation and storing token-id pairs
    // private HashMap<Long, Integer> idTokenMap = new HashMap<>();
    // private HashMap<Integer, Long> tokenIdMap = new HashMap<>();

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SessionRepository sessionRepository;

    public LoginResponse login(String userName, String password) {
        Optional<User> opUser = userRepository.findByName(userName);
        if (!opUser.isPresent()) {
            return new LoginResponse("User Name Does Not Exist", null, "");
        }
        String p = opUser.get().getPassword();
        if (!p.equals(password)) {
            return new LoginResponse("Invalid Password", null, "");
        }
        User user = opUser.get();
        Optional<Session> opSession = sessionRepository.findByUser(user);
        if(opSession.isPresent()){
            Long oldToken = opSession.get().getToken();
            return new LoginResponse("Already logged in", oldToken, userName);
        }
        Session session = new Session();
        Long token = session.getToken();
        session.setUser(user);
        sessionRepository.save(session);
        return new LoginResponse("Login success", token, userName);
    }

    public LogoutResponse logout(Long token) {
        Optional<Session> opSession = sessionRepository.findById(token);
        if (opSession.isPresent()){
            return new LogoutResponse("not online");
        }
        Session session = opSession.get();
        sessionRepository.delete(session);
        return new LogoutResponse("logged out");
    }

    public User getUserByToken(Long token) {
        Optional<Session> opSession = sessionRepository.findById(token);
        if(opSession.isPresent()) return null;
        Session session = opSession.get();
        User user = session.getUser();
        return user;
    }

    public Long getIdByToken(Long token) {
        Optional<Session> opSession = sessionRepository.findById(token);
        if(!opSession.isPresent()) return null;
        return opSession.get().getUser().getId();
    }

    public boolean isOnline(Long token){
        Optional<Session> opSession = sessionRepository.findById(token);
        return opSession.isPresent();
    }
}