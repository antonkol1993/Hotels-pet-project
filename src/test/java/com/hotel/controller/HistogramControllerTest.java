package com.hotel.controller;

import com.hotel.service.HistogramService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;


@WebMvcTest(controllers = HistogramController.class)
public class HistogramControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HistogramService histogramService;

    @Test
    @DisplayName("GET /histogram/{param} - valid param returns histogram")
    void testGetHistogramValid() throws Exception {
        Map<String, Long> histogram = Map.of("Minsk", 5L);
        Mockito.when(histogramService.getHistogram("city")).thenReturn(histogram);

        mockMvc.perform(get("/histogram/city"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Minsk").value(5));
    }

    @Test
    @DisplayName("GET /histogram/{param} - invalid param returns 400")
    void testGetHistogramInvalid() throws Exception {
        Mockito.when(histogramService.getHistogram("invalid"))
                .thenThrow(new IllegalArgumentException("Unsupported parameter"));

        mockMvc.perform(get("/histogram/invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Unsupported parameter"));
    }
}
