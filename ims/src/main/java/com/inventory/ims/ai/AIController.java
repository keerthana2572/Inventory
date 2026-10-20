package com.inventory.ims.ai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
public class AIController {
    
    @Autowired
    private AIService aiService;
    
    @PostMapping("/prompt")
    public String getAIResponse(@RequestBody String prompt) {
        try {
            return aiService.getAIResponse(prompt);
        } catch (Exception e) {
            return "Error processing AI request: " + e.getMessage();
        }
    }
}