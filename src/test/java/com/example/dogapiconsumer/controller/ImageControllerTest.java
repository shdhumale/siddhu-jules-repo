package com.example.dogapiconsumer.controller;

import com.example.dogapiconsumer.model.DogImage;
import com.example.dogapiconsumer.model.DogImageAttributes;
import com.example.dogapiconsumer.model.DogImageResponse;
import com.example.dogapiconsumer.service.DogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImageController.class)
public class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DogService dogService;

    @Test
    public void testGetRandomDogImage() throws Exception {
        DogImageResponse response = new DogImageResponse();
        DogImage dogImage = new DogImage();
        dogImage.setId("random");
        dogImage.setType("dog_image");
        DogImageAttributes attributes = new DogImageAttributes();
        attributes.setUrl("https://images.dog.ceo/breeds/beagle/n02088364_11005.jpg");
        dogImage.setAttributes(attributes);
        response.setData(dogImage);

        when(dogService.getRandomDogImage()).thenReturn(response);

        mockMvc.perform(get("/api/images/random"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value("random"))
                .andExpect(jsonPath("$.data.type").value("dog_image"))
                .andExpect(jsonPath("$.data.attributes.url").value("https://images.dog.ceo/breeds/beagle/n02088364_11005.jpg"));
    }
}
