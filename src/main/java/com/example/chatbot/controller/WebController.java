package com.example.chatbot.controller;

import com.example.chatbot.service.LLMService;
import com.example.chatbot.service.ModelManagerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

    @Autowired
    private LLMService llmService;

    @Autowired
    private ModelManagerService modelManagerService;

    @Value("${server.port:8008}")
    private String serverPort;

    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        model.addAttribute("models", modelManagerService.getAvailableModels());
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("serverPort", serverPort);
        return "index";
    }

    @PostMapping("/ask")
    public String askQuestion(@RequestParam String query,
                              @RequestParam(required = false) String model,
                              Model modelAttr,
                              HttpServletRequest request) {
        String response = llmService.processQuery(query, model);
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        modelAttr.addAttribute("query", query);
        modelAttr.addAttribute("model", model);
        modelAttr.addAttribute("response", response);
        modelAttr.addAttribute("models", modelManagerService.getAvailableModels());
        modelAttr.addAttribute("baseUrl", baseUrl);
        modelAttr.addAttribute("serverPort", serverPort);
        return "index";
    }
}