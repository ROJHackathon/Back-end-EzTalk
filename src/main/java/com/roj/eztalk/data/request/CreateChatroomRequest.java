package com.roj.eztalk.data.request;

import lombok.Data;

@Data
public class CreateChatroomRequest {
    private String name;
    private String language;
    private String description;
    private String type;
}