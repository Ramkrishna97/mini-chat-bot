package com.example.chatbot.service;

        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;
        import java.util.List;

@Service
public class LLMService {

    private static final Logger logger = LoggerFactory.getLogger(LLMService.class);
    private static final List<String> ERROR_PATTERNS = List.of(
            "token exhaust",
            "context length exceeded",
            "maximum context",
            "out of memory",
            "failed to generate",
            "model not loaded",
            "timeout"
    );

    @Autowired
    private OllamaService ollamaService;

    @Autowired
    private ModelManagerService modelManagerService;

    public String processQuery(String query, String requestedModel) {
        List<String> availableModels = modelManagerService.getAvailableModels();

        // If specific model requested, try it first
        if (requestedModel != null && !requestedModel.isEmpty()) {
            String response = tryModel(query, requestedModel);
            if (!isErrorResponse(response)) {
                return response;
            }
            logger.info("Requested model {} failed, trying alternatives", requestedModel);
        }

        // Try all models in sequence
        for (String model : availableModels) {
            if (requestedModel != null && model.equals(requestedModel)) {
                continue; // Already tried
            }

            logger.info("Trying model: {}", model);
            String response = tryModel(query, model);

            if (!isErrorResponse(response)) {
                return String.format("[Model: %s]\n%s", model, response);
            }
        }

        return "All models failed to generate a response. Please try again later.";
    }

    private String tryModel(String query, String model) {
        try {
            String response = ollamaService.generateResponse(query, model);

            // Check if response indicates an error
            if (response.startsWith("ERROR:")) {
                return response;
            }

            return response;

        } catch (Exception e) {
            logger.error("Error with model {}: {}", model, e.getMessage());
            return "ERROR: " + e.getMessage();
        }
    }

    private boolean isErrorResponse(String response) {
        if (response == null || response.startsWith("ERROR:")) {
            return true;
        }

        String lowerResponse = response.toLowerCase();
        return ERROR_PATTERNS.stream()
                .anyMatch(pattern -> lowerResponse.contains(pattern.toLowerCase()));
    }
}