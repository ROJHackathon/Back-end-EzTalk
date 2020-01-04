package com.roj.eztalk;

import com.roj.eztalk.data.*;
import com.roj.eztalk.data.request.*;
import com.roj.eztalk.data.response.*;
import com.roj.eztalk.exception.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.lang.Math;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/")
public class Controller {
    @Autowired
    UserService userService;
    @Autowired
    SessionService sessionService;
    @Autowired
    UserRepository userRepository;

    // user
    @GetMapping("user/{id}")
    public User getUser(@PathVariable Long id, HttpServletResponse response) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    // sign-up
    @PostMapping("register")
    public boolean register(@RequestBody RegisterRequest registerRequest, HttpServletResponse response) {
        boolean success = userService.register(registerRequest.getName(), registerRequest.getPassword());
        return success;
    }

    // login
    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest body, HttpServletResponse response) {
        LoginResponse responseBody = sessionService.login(body.getName(), body.getPassword());
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return responseBody;
    }

    // logout
    @PostMapping("logout")
    public LogoutResponse logout(@RequestBody Token token, HttpServletResponse response){
        return sessionService.logout(token.getToken());
    }

    // get oneline users
    @GetMapping("getOnlineUsers")
    public List<User> getOnlineUsers(){
        return sessionService.getOnlineUsers();
    }
}