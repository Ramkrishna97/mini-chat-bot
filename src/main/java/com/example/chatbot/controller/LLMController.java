package com.example.chatbot.controller;


import com.example.chatbot.model.QueryRequest;
import com.example.chatbot.service.LLMService;
import com.example.chatbot.service.ModelManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/llm")
@Tag(name = "LLM Controller", description = "Endpoints for interacting with local LLM models")
public class LLMController {

    @Autowired
    private LLMService llmService;

    @Autowired
    private ModelManagerService modelManagerService;

    @PostMapping("/query")
    @Operation(summary = "Send a query to LLM models")
    public ResponseEntity<String> postQuery(@RequestBody QueryRequest request) {
        String response = llmService.processQuery(request.getQuery(), request.getModel());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/query")
    @Operation(summary = "Get response for a query (GET method)")
    public ResponseEntity<String> getQuery(@RequestParam String query,
                                           @RequestParam(required = false) String model) {
        String response = llmService.processQuery(query, model);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/models")
    @Operation(summary = "Get list of available models")
    public ResponseEntity<List<String>> getAvailableModels() {
        return ResponseEntity.ok(modelManagerService.getAvailableModels());
    }
}