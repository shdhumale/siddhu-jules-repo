package com.example.dogapiconsumer.controller;

import com.example.dogapiconsumer.model.Breed;
import com.example.dogapiconsumer.model.DogApiListResponse;
import com.example.dogapiconsumer.model.DogApiSingleResponse;
import com.example.dogapiconsumer.model.DogImage;
import com.example.dogapiconsumer.model.DogImageAttributes;
import com.example.dogapiconsumer.model.DogImageResponse;
import com.example.dogapiconsumer.service.DogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

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

    @Test
    public void testGetBreedByIdNotFound() throws Exception {
        when(dogService.getBreedById("invalid")).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        DogImageResponse errorResponse = new DogImageResponse();
        DogImage dogImage = new DogImage();
        dogImage.setId("404");
        dogImage.setType("http_dog_image");
        DogImageAttributes attributes = new DogImageAttributes();
        attributes.setUrl("https://http.dog/404.jpg");
        dogImage.setAttributes(attributes);
        errorResponse.setData(dogImage);

        when(dogService.getHttpDogImage(404)).thenReturn(errorResponse);

        mockMvc.perform(get("/api/breeds/invalid"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.data.id").value("404"))
                .andExpect(jsonPath("$.data.attributes.url").value("https://http.dog/404.jpg"));
    }
}
