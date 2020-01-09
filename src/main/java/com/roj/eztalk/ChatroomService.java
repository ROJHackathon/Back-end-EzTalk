package com.roj.eztalk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.roj.eztalk.data.Chatroom;
import com.roj.eztalk.data.ChatroomRepository;
import com.roj.eztalk.data.Message;
import com.roj.eztalk.data.MessageRepository;
import com.roj.eztalk.data.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatroomService {
    @Autowired
    private ChatroomRepository chatroomRepository;
    @Autowired
    private MessageRepository messageRepository;
    private Map<Long, String> idNameMap = new HashMap<>();

    public boolean isNameAvailable(String name) {
        Collection<String> nameSet = idNameMap.values();
        return !nameSet.contains(name);
    }

    public Chatroom createChatroom(String name) {
        if (!isNameAvailable(name))
            return null;
        Chatroom chatroom = chatroomRepository.save(new Chatroom(name));
        this.idNameMap.put(chatroom.getId(), name);
        return chatroom;
    }

    public List<String> getChatroomList() {
        return new ArrayList<>(idNameMap.values());
    }

    public Optional<Chatroom> findById(Long id) {
        return chatroomRepository.findById(id);
    }

    public Message say(User user, Chatroom chatroom, String content){
        Message message = messageRepository.save(new Message(content, user, chatroom));
        return message;
    }
}