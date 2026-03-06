package com.example.chatbot.service;

import org.springframework.stereotype.Service;
import java.io.*;

@Service
public class GPT4AllService {

    private String lastResponse = "";
    private static final String GPT4ALL_PATH = "path/to/gpt4all-lora-quantized.bin";

    public String processQuery(String query) {
        try {
            // This is a simplified example - you'd need to use GPT4All's Java bindings
            // or run it as a subprocess
            ProcessBuilder pb = new ProcessBuilder(
                    "python",
                    "gpt4all_script.py",
                    "--prompt", query
            );

            Process process = pb.start();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            lastResponse = output.toString();
            process.waitFor();

        } catch (Exception e) {
            lastResponse = "Error: " + e.getMessage();
        }

        return lastResponse;
    }

    public String getLastResponse() {
        return lastResponse;
    }
}