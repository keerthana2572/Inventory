# Architectural Decision Record: AI Gateway Implementation

## Context
We need to integrate multiple AI Language Model providers (LiteLLM, Google Gemini, Groq, and Ollama) into our Inventory Management System to enable AI-powered features such as product suggestions, pricing strategies, and reorder recommendations.

## Decision
We decided to implement a unified AI Gateway that abstracts the differences between various LLM providers behind a common interface, allowing our application to seamlessly switch between providers without changing the calling code. We also implemented supporting components including a service layer and REST controller to expose the functionality.

## Consequences

### Positive
1. **Provider Agnostic Interface**: Our application code doesn't need to know which LLM provider is being used
2. **Easy Switching**: We can change providers through configuration without code changes
3. **Maintainability**: Centralized AI integration logic makes maintenance easier
4. **Extensibility**: Adding new providers requires minimal changes
5. **Error Handling**: Consistent error handling across all providers
6. **Integration Ready**: REST API and service layer make integration with existing systems straightforward

### Negative
1. **Abstraction Overhead**: Some provider-specific features might not be accessible through the unified interface
2. **Dependency on Multiple APIs**: We need to maintain knowledge of different API structures

## Implementation Details

### Technology Stack
- **Spring Boot 3.1.1**: Using RestTemplate for HTTP communication (since RestClient requires Spring Framework 6.1+)
- **Jackson**: For JSON parsing of API responses
- **Java 17**: As specified in the project configuration

### Supported Providers
1. **LiteLLM**: Default provider, uses OpenAI-compatible API
2. **Google Gemini**: Uses Gemini-specific API structure
3. **Groq**: OpenAI-compatible API
4. **Ollama**: OpenAI-compatible API

### Key Components

#### AIGateway.java
Main component responsible for:
- Managing configuration through Spring's @Value annotations
- Routing requests to appropriate provider-specific methods
- Handling HTTP communication with all providers
- Parsing responses according to each provider's format
- Providing consistent error handling

#### AIService.java
Service layer that wraps the AIGateway to provide business logic integration points.

#### AIController.java
REST controller that exposes AI functionality via HTTP endpoints.

### Configuration Properties
The gateway relies on the following configurable properties:
- `llm.provider`: Specifies which provider to use (default: litellm)
- `llm.api-key`: Authentication key for the selected provider (default: sk-SfyNGxhcv7RnQKbZFWX2LQ)
- `llm.model`: Model to use for inference (default: qwen-cursor)
- `llm.base-url`: Base URL for the API (default: https://litellm-qc.zycus.net/v1)

### API Integration Patterns

#### LiteLLM/Groq/Ollama (OpenAI-compatible)
- Endpoint: `/chat/completions`
- Request Format: Standard OpenAI chat completion format
- Response Format: Standard OpenAI response structure

#### Google Gemini
- Endpoint: `/models/{model}:generateContent`
- Request Format: Gemini-specific content structure
- Response Format: Gemini-specific response structure

### Error Handling
- Network errors are caught and re-thrown as generic exceptions with contextual messages
- Response parsing failures result in descriptive exceptions
- All exceptions preserve the original cause for debugging purposes

## Usage Examples

### Direct Injection
```java
@Autowired
private AIGateway aiGateway;

public String getAISuggestion(String prompt) throws Exception {
    return aiGateway.callLLM(prompt);
}
```

### Service Layer Usage
```java
@Autowired
private AIService aiService;

public String getAISuggestion(String prompt) throws Exception {
    return aiService.getAIResponse(prompt);
}
```

### REST API Usage
```bash
curl -X POST http://localhost:8080/api/ai/prompt \
  -H "Content-Type: application/json" \
  -d "Suggest a product category for a customer who likes electronics"
```

## Testing Strategy
Unit tests should verify:
1. Correct routing to provider-specific methods
2. Proper HTTP request formation for each provider
3. Accurate response parsing for each provider's format
4. Appropriate error handling and exception propagation

## Future Considerations
1. **Asynchronous Processing**: Implement async versions of the API calls for better performance
2. **Caching**: Add response caching to reduce API calls for repeated prompts
3. **Rate Limiting**: Implement rate limiting to prevent exceeding provider quotas
4. **Monitoring**: Add metrics tracking for API usage and performance
5. **Fallback Mechanisms**: Implement automatic switching between providers when one fails
6. **Streaming Responses**: Support streaming responses for long-running AI operations

## Status
Accepted

## Authors
AI Development Team

## Date
October 20, 2026