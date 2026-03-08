package com.example.chatbot.service;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ModelManagerService {

    private static final Logger logger = LoggerFactory.getLogger(ModelManagerService.class);
    private static final List<String> MODELS = Arrays.asList(
            "qwen2.5-coder:3b",
            "deepcoder:1.5b",
            "llama2:latest",
            "llama3.2:1b",
            "deepseek-r1:1.5b",
            "deepseek-r1:8b",
            "llama3.2:latest"
    );

    public void setupModels() {
        try {
            // Check if Ollama is installed
            Process checkProcess = Runtime.getRuntime().exec("ollama --version");
            int exitCode = checkProcess.waitFor();

            if (exitCode != 0) {
                logger.error("Ollama is not installed. Please install Ollama first.");
                logger.info("Visit https://ollama.ai for installation instructions.");
                return;
            }

            // Pull all models
            for (String model : MODELS) {
                logger.info("Pulling model: {}", model);
                Process pullProcess = Runtime.getRuntime().exec("ollama pull " + model);

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(pullProcess.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    logger.info(line);
                }

                pullProcess.waitFor();
                logger.info("Model {} pulled successfully", model);
            }

            logger.info("All models are ready to use!");

        } catch (Exception e) {
            logger.error("Error setting up models: {}", e.getMessage());
        }
    }

    public List<String> getAvailableModels() {
        return MODELS;
    }
}