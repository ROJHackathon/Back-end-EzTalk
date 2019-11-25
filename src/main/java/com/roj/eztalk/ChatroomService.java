package com.roj.eztalk;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import com.roj.eztalk.data.Chatroom;
import com.roj.eztalk.data.Message;

import org.springframework.stereotype.Service;


@Service
public class ChatroomService {
    private Map<Integer, Chatroom> chatroomMap;
    private Map<Integer, List<Message>> messageListMap;
}