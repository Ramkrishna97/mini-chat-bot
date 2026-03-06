package com.example.chatbot.config;

import com.example.chatbot.service.ModelManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements CommandLineRunner {

    @Autowired
    private ModelManagerService modelManagerService;

    @Override
    public void run(String... args) throws Exception {
        modelManagerService.setupModels();
    }
}