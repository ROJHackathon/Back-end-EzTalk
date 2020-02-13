package com.roj.eztalk.domain.request;

import lombok.Data;

@Data
public class CreateChatroomRequest {
    private String name;
    private String language;
    private String description;
    private String type;
}