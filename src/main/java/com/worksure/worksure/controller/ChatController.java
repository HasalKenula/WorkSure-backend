package com.worksure.worksure.controller;

import com.worksure.worksure.dto.ChatRequest;
import com.worksure.worksure.dto.ChatResponse;
import com.worksure.worksure.service.ChatService;
import com.worksure.worksure.service.ChatSessionStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:5173")
public class ChatController {

    private final ChatService chatService;
    private final ChatSessionStore sessionStore;

    @Value("${chatbot.system-prompt}")
    private String systemPrompt;

    public ChatController(ChatService chatService, ChatSessionStore sessionStore) {
        this.chatService = chatService;
        this.sessionStore = sessionStore;
    }

    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest request) {

        String reply = chatService.generateReply(
                systemPrompt,
                request.getMessage()
        );

        return new ChatResponse(reply);
    }

    @PostMapping("/reset")
    public void resetChat() {
        System.out.println("🔥 Chat session reset");
        sessionStore.clearSession("default");
    }


}
