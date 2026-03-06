package com.example.chatbot.controller;

import com.example.chatbot.service.LLMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private LLMService llmService;

    // POST endpoint to send query
    @PostMapping("/query")
    public String sendQuery(@RequestBody String query) {
        return llmService.processQuery(query);
    }

    // GET endpoint to retrieve last response
    @GetMapping("/response")
    public String getLastResponse() {
        return llmService.getLastResponse();
    }

    // Alternative: GET with query parameter
    @GetMapping("/ask")
    public String askQuestion(@RequestParam String question) {
        return llmService.processQuery(question);
    }
}