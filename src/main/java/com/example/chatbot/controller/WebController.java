package com.example.chatbot.controller;


import com.example.chatbot.service.LLMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

    @Autowired
    private LLMService llmService;

    private String lastQuery = "";

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("lastQuery", lastQuery);
        model.addAttribute("lastResponse", llmService.getLastResponse());
        return "chat";
    }

    @PostMapping("/ask")
    public String askQuestion(@RequestParam String question, Model model) {
        lastQuery = question;
        String response = llmService.processQuery(question);

        model.addAttribute("lastQuery", lastQuery);
        model.addAttribute("lastResponse", response);

        return "chat";
    }

    @GetMapping("/refresh")
    public String refresh(Model model) {
        model.addAttribute("lastQuery", lastQuery);
        model.addAttribute("lastResponse", llmService.getLastResponse());
        return "chat";
    }
}