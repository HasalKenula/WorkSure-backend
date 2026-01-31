package com.worksure.worksure.service;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatSessionStore {

    private final Map<String, ChatSession> sessions = new ConcurrentHashMap<>();

    public ChatSession getSession(String sessionId) {
        return sessions.computeIfAbsent(sessionId, id -> new ChatSession());
    }

    public void clearSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
