package com.example.dogapiconsumer.controller;

import com.example.dogapiconsumer.model.Breed;
import com.example.dogapiconsumer.model.DogApiListResponse;
import com.example.dogapiconsumer.model.DogApiSingleResponse;
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

@WebMvcTest(DogController.class)
public class DogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DogService dogService;

    @Test
    public void testGetAllBreeds() throws Exception {
        DogApiListResponse response = new DogApiListResponse();
        response.setData(Collections.emptyList());

        when(dogService.getAllBreeds()).thenReturn(response);

        mockMvc.perform(get("/api/breeds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    public void testGetBreedById() throws Exception {
        DogApiSingleResponse response = new DogApiSingleResponse();
        Breed breed = new Breed();
        breed.setId("1");
        response.setData(breed);

        when(dogService.getBreedById("1")).thenReturn(response);

        mockMvc.perform(get("/api/breeds/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value("1"));
    }
}
