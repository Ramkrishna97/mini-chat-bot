package com.example.chatbot.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class LLMService {

    private final RestTemplate restTemplate;
    private String lastResponse = "";
    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";

    public LLMService() {
        this.restTemplate = new RestTemplate();
    }

    public String processQuery(String query) {
        try {
            // Prepare request for Ollama
            Map<String, Object> request = new HashMap<>();
            request.put("model", "llama2"); // or mistral, etc.
            request.put("prompt", query);
            request.put("stream", false);

            // Send to Ollama
            Map<String, Object> response = restTemplate.postForObject(
                    OLLAMA_URL,
                    request,
                    Map.class
            );

            // Extract response
            if (response != null && response.containsKey("response")) {
                lastResponse = (String) response.get("response");
            } else {
                lastResponse = "No response from LLM";
            }

        } catch (Exception e) {
            lastResponse = "Error: " + e.getMessage();
        }

        return lastResponse;
    }

    public String getLastResponse() {
        return lastResponse.isEmpty() ? "No queries yet" : lastResponse;
    }
}