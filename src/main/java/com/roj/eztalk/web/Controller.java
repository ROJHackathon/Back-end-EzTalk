package com.roj.eztalk.web;

import com.roj.eztalk.service.*;
import com.roj.eztalk.domain.*;
import com.roj.eztalk.domain.request.*;
import com.roj.eztalk.domain.response.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    ChatroomService chatroomService;
    @Autowired
    X5gonService x5gonService;
    @Autowired
    MaterialService materialService;
    @Autowired
    RatingService ratingService;

    // testing cookies
    @GetMapping("hi")
    public String hi(HttpServletResponse response){
        Cookie cookie = new Cookie("token", "123");
        response.addCookie(cookie);
        return "hi";
    }

    // get user by token
    @GetMapping("get-user/{token}")
    public UserItem getUserByToken(@PathVariable Integer token, HttpServletResponse response) {
        if (!sessionService.isOnline(token)) {
            response.setStatus(404);
            return null;
        }
        Optional<User> opUser = sessionService.getUserByToken(token);
        if (!opUser.isPresent()) {
            response.setStatus(404);
            return null;
        }
        return new UserItem(opUser.get());
    }

    // sign-up
    @PostMapping("register")
    public UserItem register(@RequestBody RegisterRequest registerRequest, HttpServletResponse response) {
        User user = userService.register(registerRequest.getUserName(), registerRequest.getPassword());
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            response.setStatus(200);
        }
        return new UserItem(user);
    }

    // login
    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest body, HttpServletResponse response) {
        LoginResponse responseBody = sessionService.login(body.getUserName(), body.getPassword());
        if (responseBody.getToken() == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return responseBody;
    }

    // logout
    @PostMapping("logout")
    public LogoutResponse logout(@RequestBody Token token, HttpServletResponse response) {
        return sessionService.logout(token.getToken());
    }

    // get online users
    @GetMapping("get-online-users")
    public List<UserItem> getOnlineUsers() {
        return sessionService.getOnlineUsers().stream().map(x->new UserItem(x)).collect(Collectors.toList());
    }

    @PostMapping("/search-material")
    public List<MaterialItem> search(@RequestBody SearchRequest request, HttpServletResponse response) {
        try {
            response.setStatus(200);
            return x5gonService.searchMaterial(request.getText());
        } catch (Exception e) {
            response.setStatus(400);
            return null;
        }
    }

    @GetMapping("/top-word")
    public List<String> topWord() {
        // TODO: topword
        ArrayList<String> ret = new ArrayList<>();
        ret.add("topword 1");
        ret.add("topword 2");
        ret.add("topword 3");
        ret.add("topword 4");
        ret.add("topword 5");
        ret.add("topword 6");
        ret.add("topword 7");
        ret.add("topword 8");
        ret.add("topword 9");
        ret.add("topword 10");
        return ret;
    }

    @PostMapping("/set-email")
    public UserItem setEmail(@RequestBody SetEmailRequest request, HttpServletResponse response) {
        Integer token = request.getToken();
        String email = request.getEmail();
        Long id = sessionService.getIdByToken(token);
        if (id == null) {
            response.setStatus(400);
            return null;
        }
        User user = userService.setEmail(id, email);
        if (user == null) {
            response.setStatus(400);
            return null;
        }
        return new UserItem(user);
    }

    @PostMapping("/set-preference")
    public UserItem setPreference(@RequestBody SetPreferenceRequest request, HttpServletResponse response) {
        Integer token = request.getToken();
        String preference = request.getPreference();
        Long id = sessionService.getIdByToken(token);
        if (id == null) {
            response.setStatus(400);
            return null;
        }
        User user = userService.setPreference(id, preference);
        if (user == null) {
            response.setStatus(400);
            return null;
        }
        return new UserItem(user);
    }

    @PostMapping("/set-language")
    public UserItem setLanguage(@RequestBody SetLanguageRequest request, HttpServletResponse response) {
        Integer token = request.getToken();
        String language = request.getLanguage();
        Long id = sessionService.getIdByToken(token);
        if (id == null) {
            response.setStatus(400);
            return null;
        }
        User user = userService.setLanguage(id, language);
        if (user == null) {
            response.setStatus(400);
            return null;
        }
        return new UserItem(user);
    }

    @PostMapping("/set-target-language")
    public UserItem setTargetLanguage(@RequestBody SetTargetLanguageRequest request, HttpServletResponse response) {
        Integer token = request.getToken();
        String language = request.getTargetLanguage();
        Long id = sessionService.getIdByToken(token);
        if (id == null) {
            response.setStatus(400);
            return null;
        }
        User user = userService.setTargetLanguage(id, language);
        if (user == null) {
            response.setStatus(400);
            return null;
        }
        return new UserItem(user);
    }

    @PostMapping("/feed")
    public List<MaterialItem> feed(@RequestBody FeedRequest request, HttpServletResponse response) {
        Optional<User> opUser = sessionService.getUserByToken(request.getToken());
        if (!opUser.isPresent()) {
            response.setStatus(400);
            return null;
        }
        User user = opUser.get();
        String preference = user.getPreference();
        if(preference == null) {
            preference = "English";
        }
        Integer page = request.getPage();
        List<MaterialItem> retval;
        try {
             retval = x5gonService.recommendMaterial(preference, page);
        } catch (Exception e) {
            response.setStatus(400);
            return null;
        }
        response.setStatus(200);
        return retval;
    }
}