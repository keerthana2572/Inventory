package com.inventory.ims.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.ims.strategy.StrategyConfigurationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StrategyController.class)
class StrategyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StrategyConfigurationService strategyConfigurationService;

    @BeforeEach
    void setUp() {
        // Setup default mock behavior
        when(strategyConfigurationService.getActivePricingStrategy()).thenReturn("ruleBased");
        when(strategyConfigurationService.getActiveReorderStrategy()).thenReturn("ruleBased");
    }

    @Test
    void testGetAvailableStrategies_Success() throws Exception {
        // Arrange
        // This would normally be handled by the StrategyRegistry, but we'll mock it
        Map<String, Object> response = new HashMap<>();
        response.put("pricingStrategies", Arrays.asList("ruleBased"));
        response.put("reorderStrategies", Arrays.asList("ruleBased"));

        // Act & Assert
        mockMvc.perform(get("/api/strategies/available")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pricingStrategies").isArray())
                .andExpect(jsonPath("$.reorderStrategies").isArray());
    }

    @Test
    void testGetActiveStrategies_Success() throws Exception {
        // Arrange
        when(strategyConfigurationService.getActivePricingStrategy()).thenReturn("ruleBased");
        when(strategyConfigurationService.getActiveReorderStrategy()).thenReturn("ruleBased");

        // Act & Assert
        mockMvc.perform(get("/api/strategies/active")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pricingStrategy").value("ruleBased"))
                .andExpect(jsonPath("$.reorderStrategy").value("ruleBased"));
    }

    @Test
    void testSwitchStrategies_Success() throws Exception {
        // Arrange
        // No exception thrown means success

        // Act & Assert
        mockMvc.perform(post("/api/strategies/switch")
                .param("pricingStrategy", "ruleBased")
                .param("reorderStrategy", "ruleBased")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully switched to pricing strategy: ruleBased and reorder strategy: ruleBased"));
    }

    @Test
    void testSwitchStrategies_MissingPricingStrategy() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/strategies/switch")
                .param("reorderStrategy", "ruleBased")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSwitchStrategies_MissingReorderStrategy() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/strategies/switch")
                .param("pricingStrategy", "ruleBased")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}