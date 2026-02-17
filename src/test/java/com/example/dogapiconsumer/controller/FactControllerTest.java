package com.example.dogapiconsumer.controller;

import com.example.dogapiconsumer.model.DogImage;
import com.example.dogapiconsumer.model.DogImageAttributes;
import com.example.dogapiconsumer.model.DogImageResponse;
import com.example.dogapiconsumer.model.FactResponse;
import com.example.dogapiconsumer.service.DogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import com.example.dogapiconsumer.advice.UnifiedResponseAdvice;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(FactController.class)
@Import(UnifiedResponseAdvice.class)
public class FactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DogService dogService;

    @BeforeEach
    public void setup() {
        DogImageResponse imageResponse = new DogImageResponse();
        DogImage dogImage = new DogImage();
        dogImage.setId("200");
        dogImage.setType("http_dog_image");
        DogImageAttributes attributes = new DogImageAttributes();
        attributes.setUrl("https://http.dog/200.jpg");
        dogImage.setAttributes(attributes);
        imageResponse.setData(dogImage);

        when(dogService.getHttpDogImage(200)).thenReturn(imageResponse);
    }

    @Test
    public void testGetFacts() throws Exception {
        FactResponse response = new FactResponse();
        response.setData(Collections.emptyList());

        when(dogService.getFacts()).thenReturn(response);

        mockMvc.perform(get("/api/facts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.data").isArray())
                .andExpect(jsonPath("$.image.data.id").value("200"));
    }
}
