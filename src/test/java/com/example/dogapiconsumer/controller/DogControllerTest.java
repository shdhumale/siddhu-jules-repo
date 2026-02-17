package com.example.dogapiconsumer.controller;

import com.example.dogapiconsumer.model.Breed;
import com.example.dogapiconsumer.model.DogApiListResponse;
import com.example.dogapiconsumer.model.DogApiSingleResponse;
import com.example.dogapiconsumer.model.DogImage;
import com.example.dogapiconsumer.model.DogImageAttributes;
import com.example.dogapiconsumer.model.DogImageResponse;
import com.example.dogapiconsumer.service.DogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import com.example.dogapiconsumer.advice.UnifiedResponseAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(DogController.class)
@Import(UnifiedResponseAdvice.class)
public class DogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DogService dogService;

    private DogImageResponse mockImageResponse(int code) {
        DogImageResponse imageResponse = new DogImageResponse();
        DogImage dogImage = new DogImage();
        dogImage.setId(String.valueOf(code));
        dogImage.setType("http_dog_image");
        DogImageAttributes attributes = new DogImageAttributes();
        attributes.setUrl("https://http.dog/" + code + ".jpg");
        dogImage.setAttributes(attributes);
        imageResponse.setData(dogImage);
        return imageResponse;
    }

    @BeforeEach
    public void setup() {
        when(dogService.getHttpDogImage(200)).thenReturn(mockImageResponse(200));
        when(dogService.getHttpDogImage(404)).thenReturn(mockImageResponse(404));
    }

    @Test
    public void testGetAllBreeds() throws Exception {
        DogApiListResponse response = new DogApiListResponse();
        response.setData(Collections.emptyList());

        when(dogService.getAllBreeds()).thenReturn(response);

        mockMvc.perform(get("/api/breeds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.data").isArray())
                .andExpect(jsonPath("$.image.data.id").value("200"));
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
                .andExpect(jsonPath("$.response.data.id").value("1"))
                .andExpect(jsonPath("$.image.data.id").value("200"));
    }

    @Test
    public void testGetBreedByIdNotFound() throws Exception {
        when(dogService.getBreedById("invalid")).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/breeds/invalid"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.response.error").value("404 NOT_FOUND"))
                .andExpect(jsonPath("$.image.data.id").value("404"))
                .andExpect(jsonPath("$.image.data.attributes.url").value("https://http.dog/404.jpg"));
    }
}
