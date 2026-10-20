package com.inventory.ims.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

/**
 * Provider-specific HTTP for Gemini, Groq, Ollama, and LiteLLM.
 * Returns raw text — parsing, validation, fallback are yours.
 */
@Component
public class AIGateway {
    
    @Value("${llm.provider:litellm}")
    private String provider;
    
    @Value("${llm.api-key:sk-SfyNGxhcv7RnQKbZFWX2LQ}")
    private String apiKey;
    
    @Value("${llm.model:qwen-cursor}")
    private String model;
    
    @Value("${llm.base-url:https://litellm-qc.zycus.net/v1}")
    private String baseUrl;
    
    private final RestTemplate http;
    private final ObjectMapper objectMapper;
    
    public AIGateway() {
        this.http = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }
    
    public String callLLM(String prompt) throws Exception {
        try {
            switch (provider.toLowerCase()) {
                case "gemini":
                    return callGemini(prompt);
                case "groq":
                    return callOpenAICompatible(prompt, baseUrl + "/chat/completions");
                case "ollama":
                    return callOpenAICompatible(prompt, baseUrl + "/chat/completions");
                case "litellm":
                    return callLiteLLM(prompt);
                default:
                    throw new IllegalStateException("Unknown provider: " + provider);
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new Exception("LLM call failed: " + e.getMessage(), e);
        }
    }
    
    private String callLiteLLM(String prompt) throws Exception {
        String url = baseUrl + "/chat/completions";
        
        Map<String, Object> requestBody = Map.of(
            "model", model,
            "messages", List.of(
                Map.of("role", "user", "content", prompt)
            ),
            "temperature", 0.7,
            "max_tokens", 1000
        );
        
        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");
        
        HttpEntity<Map<String, Object>> entity = 
            new HttpEntity<>(requestBody, headers);
        
        String response = http.postForObject(url, entity, String.class);
        
        // Parse the JSON response to extract the content
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode choices = jsonNode.get("choices");
            if (choices != null && choices.isArray() && choices.size() > 0) {
                JsonNode message = choices.get(0).get("message");
                if (message != null) {
                    JsonNode content = message.get("content");
                    if (content != null) {
                        return content.asText();
                    }
                }
            }
            // If we can't parse the response properly, throw an exception
            throw new Exception("Failed to parse LLM response: " + response);
        } catch (Exception e) {
            // Re-throw the exception with more context
            throw new Exception("Failed to parse LLM response: " + e.getMessage(), e);
        }
    }
    
    private String callGemini(String prompt) throws Exception {
        // Gemini uses a different endpoint structure
        String url = baseUrl + "/models/" + model + ":generateContent";
        
        // Create request body for Gemini
        Map<String, Object> contents = new HashMap<>();
        contents.put("parts", List.of(Map.of("text", prompt)));
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", List.of(contents));
        
        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");
        
        HttpEntity<Map<String, Object>> entity = 
            new HttpEntity<>(requestBody, headers);

        String response = http.postForObject(url, entity, String.class);
        
        // Parse the JSON response to extract the content
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode candidates = jsonNode.get("candidates");
            if (candidates != null && candidates.isArray() && candidates.size() > 0) {
                JsonNode content = candidates.get(0).get("content");
                if (content != null) {
                    JsonNode parts = content.get("parts");
                    if (parts != null && parts.isArray() && parts.size() > 0) {
                        JsonNode text = parts.get(0).get("text");
                        if (text != null) {
                            return text.asText();
                        }
                    }
                }
            }
            throw new Exception("Failed to parse Gemini response: " + response);
        } catch (Exception e) {
            throw new Exception("Failed to parse Gemini response: " + e.getMessage(), e);
        }
    }
    
    private String callOpenAICompatible(String prompt, String url) throws Exception {
        Map<String, Object> requestBody = Map.of(
            "model", model,
            "messages", List.of(
                Map.of("role", "user", "content", prompt)
            ),
            "temperature", 0.7,
            "max_tokens", 1000
        );
        
        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");
        
        HttpEntity<Map<String, Object>> entity = 
            new HttpEntity<>(requestBody, headers);
        
        String response = http.postForObject(url, entity, String.class);
        
        // Parse the JSON response to extract the content
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode choices = jsonNode.get("choices");
            if (choices != null && choices.isArray() && choices.size() > 0) {
                JsonNode message = choices.get(0).get("message");
                if (message != null) {
                    JsonNode content = message.get("content");
                    if (content != null) {
                        return content.asText();
                    }
                }
            }
            throw new Exception("Failed to parse OpenAI-compatible response: " + response);
        } catch (Exception e) {
            throw new Exception("Failed to parse OpenAI-compatible response: " + e.getMessage(), e);
        }
    }
}