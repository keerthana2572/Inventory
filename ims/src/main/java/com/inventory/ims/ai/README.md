# AI Integration for Inventory Management System

## Overview
This module provides integration with multiple AI Language Model providers to enable intelligent features in the Inventory Management System.

## Supported Providers
1. **LiteLLM** (Default) - OpenAI-compatible API
2. **Google Gemini** - Google's generative AI model
3. **Groq** - High-performance AI inference
4. **Ollama** - Local AI model serving

## Configuration
The AI integration can be configured using the following properties in `application.properties`:

```properties
# Provider selection
llm.provider=litellm

# Authentication
llm.api-key=your-api-key-here

# Model selection
llm.model=qwen-cursor

# API endpoint
llm.base-url=https://litellm-qc.zycus.net/v1
```

## Usage

### Direct Component Injection
```java
@Autowired
private AIGateway aiGateway;

public String getAIResponse(String prompt) throws Exception {
    return aiGateway.callLLM(prompt);
}
```

### Service Layer Usage
```java
@Autowired
private AIService aiService;

public String getProductSuggestion(String customerProfile) throws Exception {
    return aiService.getAIResponse("Suggest products for customer: " + customerProfile);
}
```

### REST API Access
```bash
curl -X POST http://localhost:8080/api/ai/prompt \
  -H "Content-Type: application/json" \
  -d "Suggest inventory optimization strategies for electronics category"
```

## Provider-Specific Endpoints

### LiteLLM/Groq/Ollama
- **Endpoint**: `/chat/completions`
- **Method**: POST
- **Format**: OpenAI-compatible chat completions

### Google Gemini
- **Endpoint**: `/models/{model}:generateContent`
- **Method**: POST
- **Format**: Gemini-specific content generation

## Error Handling
All AI calls can throw `Exception` which should be handled appropriately in your application code. Common error scenarios include:
- Network connectivity issues
- Authentication failures
- Rate limiting
- Invalid response formats

## Extending to New Providers
To add support for a new AI provider:

1. Add a new case to the switch statement in `AIGateway.callLLM()`
2. Implement a provider-specific method following the pattern of existing methods
3. Handle the specific request/response format for the new provider
4. Update the configuration documentation

## Testing
Unit tests are located in `src/test/java/com/inventory/ims/ai/` and should be extended when adding new providers or functionality.

## Security Considerations
- API keys should be stored securely and not hard-coded
- Validate all inputs to prevent injection attacks
- Implement appropriate rate limiting to prevent abuse
- Monitor API usage for unusual patterns