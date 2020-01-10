package com.roj.eztalk;

import com.roj.eztalk.data.*;
import com.roj.eztalk.data.request.*;
import com.roj.eztalk.data.response.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
    ChatroomService chatroomService;
    @Autowired
    X5gonService x5gonService;
    @Autowired
    MaterialService materialService;

    // user
    @GetMapping("user/{id}")
    public User getUser(@PathVariable Long id, HttpServletResponse response) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    // user history
    @GetMapping("user/{id}/history")
    public List<String> getHistory(@PathVariable Long id, HttpServletResponse response) {
        ArrayList<String> retval = new ArrayList<>();
        retval.add("history 1");
        retval.add("history 2");
        retval.add("history 3");
        retval.add("history 4");
        retval.add("history 5");
        return retval;
    }

    // sign-up
    @PostMapping("register")
    public User register(@RequestBody RegisterRequest registerRequest, HttpServletResponse response) {
        User user = userService.register(registerRequest.getName(), registerRequest.getPassword());
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            response.setStatus(200);
        }
        return user;
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
    public LogoutResponse logout(@RequestBody Token token, HttpServletResponse response) {
        return sessionService.logout(token.getToken());
    }

    // get oneline users
    @GetMapping("get-oneline-users")
    public List<User> getOnlineUsers() {
        return sessionService.getOnlineUsers();
    }

    // create chatroom
    @PostMapping("create-chatroom")
    public Chatroom createChatroom(@RequestBody CreateChatroomRequest request, HttpServletResponse response) {
        Chatroom chatroom = chatroomService.createChatroom(request.getName());
        if (chatroom == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            response.setStatus(200);
        }
        return chatroom;
    }

    // send message
    @PostMapping("chatroom/{id}/say")
    public Message say(@RequestBody SendMessageRequest request, @PathVariable Long id, HttpServletResponse response) {
        Integer token = request.getToken();
        if (!sessionService.isOnline(token)) {
            // the token is not valid
            response.setStatus(400);
            return null;
        }
        Optional<User> opUser = sessionService.getUserByToken(token);
        if (!opUser.isPresent()) {
            response.setStatus(400);
            return null;
        }
        User user = opUser.get();

        Optional<Chatroom> opChatroom = chatroomService.findById(id);
        if (!opChatroom.isPresent()) {
            response.setStatus(404);
            return null;
        }
        Chatroom chatroom = opChatroom.get();

        Message message = chatroomService.say(user, chatroom, request.getContent());
        return message;
    }

    // get chatroom info
    @GetMapping("chatroom/{id}")
    public Chatroom getChatroomInfo(@PathVariable Long id, HttpServletResponse response) {
        Optional<Chatroom> chatroom = chatroomService.findById(id);
        if (chatroom.isPresent()) {
            response.setStatus(200);
            return chatroom.get();
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    // get members of a chatroom with given id
    @GetMapping("chatroom/{id}/get-user")
    public List<User> getMembersOfChatroom(@PathVariable Long id, HttpServletResponse response) {
        Optional<Chatroom> opChatroom = chatroomService.findById(id);
        if (!opChatroom.isPresent()) {
            response.setStatus(404);
            return null;
        } else {
            Chatroom chatroom = opChatroom.get();
            response.setStatus(200);
            return chatroom.getMembers();
        }
    }

    // get messages of a chatroom with given id
    @GetMapping("chatroom/{id}/get-messages")
    public List<Message> getMessages(@PathVariable Long id, HttpServletResponse response) {
        Optional<Chatroom> opChatroom = chatroomService.findById(id);
        if (!opChatroom.isPresent()) {
            response.setStatus(404);
            return null;
        } else {
            Chatroom chatroom = opChatroom.get();
            response.setStatus(200);
            return chatroom.getMessages();
        }
    }

    @PostMapping("/search-material")
    public List<MaterialAdd> search(@RequestBody SearchRequest request, HttpServletResponse response) {
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
        // do this
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

    @GetMapping("/material/{id}")
    public MaterialAdd getMaterial(@PathVariable Long id, HttpServletResponse response) {
        try {
            response.setStatus(200);
            return x5gonService.getMaterialById(id);
        } catch (Exception e) {
            response.setStatus(400);
            return null;
        }
    }

    @PostMapping("/material/{id}/comment")
    public Comment comment(@RequestBody CommentRequest request, @PathVariable Long id, HttpServletResponse response) {
        String content = request.getContent();
        Integer token = request.getToken();

        Optional<Material> opMaterial = materialService.findById(id);
        if(!opMaterial.isPresent()){
            response.setStatus(404);
            return null;
        }

        Optional<User> opUser = sessionService.getUserByToken(token);
        if(!opUser.isPresent()){
            response.setStatus(400);
            return null;
        }

        return materialService.comment(opUser.get(), opMaterial.get(), content);
    }

    @GetMapping("/material/{id}/get-comments")
    public List<Comment> testId(@PathVariable Long id, HttpServletResponse response) {
        Optional<Material> opMaterial = materialService.findById(id);
        if(!opMaterial.isPresent()){
            response.setStatus(404);
            return null;
        }
        Material material = opMaterial.get();
        response.setStatus(200);
        return material.getComments();
    }

}