package com.nextgenpaper.NextGenPaper.dto.chat;

// ChatRequest.java
import lombok.Data;

import java.util.List;

@Data
public class ChatRequest {
    private String model;
    private List<Message> messages;
    public ChatRequest() {}
    public ChatRequest(String model, List<Message> messages) {
        this.model = model;
        this.messages = messages;
    }
}

