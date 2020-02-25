package com.roj.eztalk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

import com.roj.eztalk.domain.Chatroom;
import com.roj.eztalk.domain.ChatroomItem;
import com.roj.eztalk.dao.ChatroomRepository;
import com.roj.eztalk.domain.Message;
import com.roj.eztalk.dao.MessageRepository;
import com.roj.eztalk.dao.UserRepository;
import com.roj.eztalk.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatroomService {
    // for creating new chatroom, getting list of chatroom and messaging
    @Autowired
    private ChatroomRepository chatroomRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    public Chatroom createChatroom(String name, String type, String language, String description) {
        Optional<Chatroom> opChatroom = chatroomRepository.findByName(name);
        if(opChatroom.isPresent()) return null;
        Chatroom chatroom = new Chatroom(name, description, language, type);
        chatroom = chatroomRepository.save(chatroom);
        return chatroom;
    }

    public List<ChatroomItem> getChatroomList() {
        return chatroomRepository.findAll().stream().map(x->new ChatroomItem(x)).collect(Collectors.toList());
    }

    public List<ChatroomItem> getPublicChatroomList() {
        return chatroomRepository.findByType("public").stream().map(x->new ChatroomItem(x)).collect(Collectors.toList());
    }

    public List<ChatroomItem> getPrivateChatroomList(Long id) {
        List<Chatroom> chatroomList = chatroomRepository.findByType("private").stream()
        .collect(Collectors.toList());
        List<ChatroomItem> retChatroom = new ArrayList<>();
        for(Chatroom c : chatroomList){
            User u1 = c.getMembers().get(0);
            User u2 = c.getMembers().get(1);
            String name = u1.getId() == id ? u2.getName() : u1.getName();
            if(u1.getId() == id || u2.getId() == id){
                ChatroomItem item = new ChatroomItem(c);
                item.setName(name);
                retChatroom.add(item);
            }
        }
        return retChatroom;
    }

    public Optional<Chatroom> findById(Long id) {
        return chatroomRepository.findById(id);
    }

    public Message say(User user, Chatroom chatroom, String content){
        LocalDateTime dateTime = LocalDateTime.now();
        Message message = new Message(content, dateTime.toString(), user, chatroom);
        message = messageRepository.save(message);
        return message;
    }

    public Chatroom befriend(Long uid1, Long uid2){
        Chatroom chatroom = new Chatroom();
        Optional<User> optionalUser1 = userRepository.findById(uid1);
        Optional<User> optionalUser2 = userRepository.findById(uid2);
        if(!optionalUser1.isPresent() || !optionalUser2.isPresent()) return null;
        List<User> userList = new ArrayList<>();
        User user1 = optionalUser1.get();
        User user2 = optionalUser2.get();
        userList.add(optionalUser1.get());
        userList.add(optionalUser2.get());
        chatroom.setMembers(userList);
        chatroom.setType("private");
        chatroom = chatroomRepository.save(chatroom);
        user1.addChatroom(chatroom);
        user2.addChatroom(chatroom);
        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);
        return chatroom;
    }
}