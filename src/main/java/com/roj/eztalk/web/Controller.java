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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@Api(tags = {"Main View Operation"})
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
    @ApiOperation(value = "Test sending a cookie", tags="Cookie Management")
    public String hi(HttpServletResponse response){
        Cookie cookie = new Cookie("token", "123");
        response.addCookie(cookie);
        return "hi";
    }

    // get user by token
    @GetMapping("get-user/{token}")
    @ApiOperation(value = "Get User information by the client current token", tags={"User Management","Admin Operations"})
    public UserItem getUserByToken(@PathVariable Long token, HttpServletResponse response) {
        if (!sessionService.isOnline(token)) {
            response.setStatus(404);
            return null;
        }
        User user = sessionService.getUserByToken(token);
        if (user == null) {
            response.setStatus(404);
            return null;
        }
        return new UserItem(user);
    }

    // sign-up
    @PostMapping("register")
    @ApiOperation(value = "Register a user into the database", tags = "User Management")
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
    @ApiOperation(value = "Login a user and generate a token valid for this login session only", tags = "User Management")
    public LoginResponse login(@RequestBody LoginRequest body, HttpServletResponse response) {
        LoginResponse responseBody = sessionService.login(body.getUserName(), body.getPassword());
        if (responseBody.getToken() == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return responseBody;
    }

    // logout
    @PostMapping("logout")
    @ApiOperation(value = "Log out a user and destroy the current token", tags = "User Management")
    public LogoutResponse logout(@RequestBody Token token, HttpServletResponse response) {
        return sessionService.logout(token.getToken());
    }

    // get online users
    // @GetMapping("get-online-users")
    // @ApiOperation(value = "Get all current online users", tags = "Admin Operations")
    // public List<UserItem> getOnlineUsers() {
    //     return sessionService.getOnlineUsers().stream().map(x->new UserItem(x)).collect(Collectors.toList());
    // }

    @PostMapping("/search-material")
    @ApiOperation(value = "Search material by its keywords provided", tags = "Material Management")
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
    @ApiOperation(value = "Get current top searches recently made", tags = "Material Management")
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
    @ApiOperation(value = "Set the user's email by using the current user token", tags = "User Management")
    public UserItem setEmail(@RequestBody SetEmailRequest request, HttpServletResponse response) {
        Long token = request.getToken();
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
    @ApiOperation(value = "Set the user's preference by using the current user token", tags = "User Management")
    public UserItem setPreference(@RequestBody SetPreferenceRequest request, HttpServletResponse response) {
        Long token = request.getToken();
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
    @ApiOperation(value = "Set the user's mother language by using the current user token", tags = "User Management")
    public UserItem setLanguage(@RequestBody SetLanguageRequest request, HttpServletResponse response) {
        Long token = request.getToken();
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
    @ApiOperation(value = "Set the user's target language by using the current user token", tags = "User Management")
    public UserItem setTargetLanguage(@RequestBody SetTargetLanguageRequest request, HttpServletResponse response) {
        Long token = request.getToken();
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
    @ApiOperation(value = "Get current feed recommendations for the user", tags = "Material Management")
    public List<MaterialItem> feed(@RequestBody FeedRequest request, HttpServletResponse response) {
        User user = sessionService.getUserByToken(request.getToken());
        if (user == null) {
            response.setStatus(400);
            return null;
        }
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
    @PostMapping("/feed2")
    @ApiOperation(value = "Get current feed recommendations for the user", tags = "Material Management")
    public List<MaterialItem> feed2(@RequestBody FeedRequest request, HttpServletResponse response) {
        return x5gonService.getFeed(request.getToken());
    }
}