package com.worksure.worksure.controller;

import com.worksure.worksure.dto.ChatRequest;
import com.worksure.worksure.dto.ChatResponse;
import com.worksure.worksure.service.ChatService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "http://localhost:5173")
public class ChatController {

    private final ChatService chatService;

    @Value("${chatbot.system-prompt}")
    private String systemPrompt;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest request) {

        if (!isDentalQuestion(request.getMessage())) {
            return new ChatResponse(
                    "I can help only with WorkSure services 😊\n" +
                            "You can ask things like:\n" +
                            "• Finding a plumber or electrician\n" +
                            "• Service prices or working hours\n" +
                            "• How booking works\n" +
                            "• Home repair and maintenance help 🔧"
            );
        }

        String reply = chatService.generateReply(
                systemPrompt,
                request.getMessage()
        );

        return new ChatResponse(reply);
    }

    private boolean isDentalQuestion(String msg) {
        String text = msg.toLowerCase();

        // Allow greetings
        if (text.matches(".*\\b(hi|hello|hey|good morning|good evening)\\b.*")) {
            return true;
        }

        return text.matches(
                ".*(plumber|plumbing|electrician|electric|carpenter|painter|painting|" +
                        "repair|fix|install|service|worker|technician|home|house|job|" +
                        "price|cost|charge|hour|available|booking|appointment|near me).*"
        );
    }

}
