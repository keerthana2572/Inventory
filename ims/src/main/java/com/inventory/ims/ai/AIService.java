package com.inventory.ims.ai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AIService {
    
    @Autowired
    private AIGateway aiGateway;
    
    public String getAIResponse(String prompt) throws Exception {
        return aiGateway.callLLM(prompt);
    }
}