package com.example.dogapiconsumer.controller;

import com.example.dogapiconsumer.model.FactResponse;
import com.example.dogapiconsumer.service.DogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(FactController.class)
public class FactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DogService dogService;

    @Test
    public void testGetFacts() throws Exception {
        FactResponse response = new FactResponse();
        response.setData(Collections.emptyList());

        when(dogService.getFacts()).thenReturn(response);

        mockMvc.perform(get("/api/facts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }
}
