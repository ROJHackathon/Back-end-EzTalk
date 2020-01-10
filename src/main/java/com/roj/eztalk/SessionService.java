package com.roj.eztalk;

import java.time.LocalDateTime;
import com.roj.eztalk.data.*;
import com.roj.eztalk.data.response.LoginResponse;
import com.roj.eztalk.data.response.LogoutResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class SessionService {
    private HashMap<Long, Integer> idTokenMap = new HashMap<>();
    private HashMap<Integer, Long> tokenIdMap = new HashMap<>();

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    /**
     * 
     * @param name the user name
     * @return a token for a session
     */
    public int getToken(String name) {
        String dateTime = LocalDateTime.now().toString();
        String userName = name;
        Integer token = String.format("%s:%s", userName, dateTime).hashCode();
        while (tokenIdMap.containsKey(token)) {
            // dangerous code !!!
            token++;
        }
        return token;
    }

    public LoginResponse login(String userName, String password) {
        Optional<User> user = userRepository.findByName(userName);
        if (!user.isPresent()) {
            return new LoginResponse("User Name Does Not Exist", null, "");
        }
        String p = user.get().getPassword();
        if (!p.equals(password)) {
            return new LoginResponse("Invalid Password", null, "");
        }
        int token = getToken(userName);
        if (token == 0)
            return new LoginResponse("Retry later", null, "");

        Long uid = user.get().getId();
        if (idTokenMap.containsKey(uid)) {
            Integer oldToken = idTokenMap.get(uid);
            return new LoginResponse("Already logged in", oldToken, userName);
        }
        tokenIdMap.put(token, uid);
        idTokenMap.put(uid, token);
        return new LoginResponse("Login success", token, userName);
    }

    public LogoutResponse logout(Integer token) {
        if (!tokenIdMap.containsKey(token)) {
            return new LogoutResponse("not online");
        }
        Long id = tokenIdMap.get(token);
        idTokenMap.remove(id);
        tokenIdMap.remove(token);
        return new LogoutResponse("logged out");
    }

    public boolean isTokenValid(Integer token) {
        return tokenIdMap.containsKey(token);
    }

    public boolean isOnline(Integer token) {
        return tokenIdMap.containsKey(token);
    }

    public List<User> getOnlineUsers() {
        List<User> retval = new ArrayList<>();
        for (Long uid : idTokenMap.keySet()) {
            Optional<User> user = userRepository.findById(uid);
            if (user.isPresent())
                retval.add(user.get());
        }
        return retval;
    }

    public Optional<User> getUserByToken(Integer token) {
        Long id = this.tokenIdMap.get(token);
        return userRepository.findById(id);
    }

    public Long getIdByToken(Integer token) {
        if(!tokenIdMap.containsKey(token)){
            return null;
        }
        return this.tokenIdMap.get(token);
    }
}