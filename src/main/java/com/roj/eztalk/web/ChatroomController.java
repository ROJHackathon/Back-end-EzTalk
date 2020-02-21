package com.roj.eztalk.web;

import com.roj.eztalk.service.*;
import com.roj.eztalk.domain.*;
import com.roj.eztalk.domain.request.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
@Api(tags = {"ChatRoom Management"})
@CrossOrigin(maxAge = 3600)
@RequestMapping("/api/")
public class ChatroomController {
    @Autowired
    ChatroomService chatroomService;

    @Autowired
    SessionService sessionService;
    
    // create chatroom
    @PostMapping("create-chatroom")
    public Chatroom createChatroom(@RequestBody CreateChatroomRequest request, HttpServletResponse response) {
        Chatroom chatroom = chatroomService.createChatroom(request.getName(), request.getType(), request.getLanguage(),
                request.getDescription());
        if (chatroom == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            response.setStatus(200);
        }
        return chatroom;
    }

    // get chatroom list
    @GetMapping("chatroom-list")
    @ApiOperation(value = "Get a list of all chatRooms", tags = "Admin Operations")
    public List<Chatroom> getChatroomList() {
        return chatroomService.getChatroomList();
    }

    // send message
    @PostMapping("chatroom/{id}/say")
    public MessageItem say(@RequestBody SendMessageRequest request, @PathVariable Long id, HttpServletResponse response) {
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
        return new MessageItem(message);
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
    public List<UserItem> getMembersOfChatroom(@PathVariable Long id, HttpServletResponse response) {
        Optional<Chatroom> opChatroom = chatroomService.findById(id);
        if (!opChatroom.isPresent()) {
            response.setStatus(404);
            return null;
        } else {
            Chatroom chatroom = opChatroom.get();
            response.setStatus(200);
            return chatroom.getMembers().stream().map(x->new UserItem(x)).collect(Collectors.toList());
        }
    }

    // get messages of a chatroom with given id
    @GetMapping("chatroom/{id}/get-messages")
    public List<MessageItem> getMessages(@PathVariable Long id, HttpServletResponse response) {
        Optional<Chatroom> opChatroom = chatroomService.findById(id);
        if (!opChatroom.isPresent()) {
            response.setStatus(404);
            return null;
        } else {
            Chatroom chatroom = opChatroom.get();
            response.setStatus(200);
            List<Message> messages = chatroom.getMessages();
            Collections.sort(messages);
            return messages.stream().map(x->new MessageItem(x)).collect(Collectors.toList());
        }
    }
}