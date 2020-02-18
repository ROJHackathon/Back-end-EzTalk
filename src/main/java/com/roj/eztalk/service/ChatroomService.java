package com.roj.eztalk.service;

import java.util.List;
import java.util.Optional;

import com.roj.eztalk.domain.Chatroom;
import com.roj.eztalk.dao.ChatroomRepository;
import com.roj.eztalk.domain.Message;
import com.roj.eztalk.dao.MessageRepository;
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

    public Chatroom createChatroom(String name, String type, String language, String description) {
        Optional<Chatroom> opChatroom = chatroomRepository.findByName(name);
        if(opChatroom.isPresent()) return null;
        Chatroom chatroom = new Chatroom(name, description, language, type);
        chatroom = chatroomRepository.save(chatroom);
        return chatroom;
    }

    public List<Chatroom> getChatroomList() {
        return chatroomRepository.findAll();
    }

    public Optional<Chatroom> findById(Long id) {
        return chatroomRepository.findById(id);
    }

    public Message say(User user, Chatroom chatroom, String content){
        Message message = messageRepository.save(new Message(content, user, chatroom));
        return message;
    }
}