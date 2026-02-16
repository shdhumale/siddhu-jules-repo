package com.example.dogapiconsumer.service;

import com.example.dogapiconsumer.model.DogApiListResponse;
import com.example.dogapiconsumer.model.DogApiSingleResponse;
import com.example.dogapiconsumer.model.DogCeoResponse;
import com.example.dogapiconsumer.model.DogImage;
import com.example.dogapiconsumer.model.DogImageAttributes;
import com.example.dogapiconsumer.model.DogImageResponse;
import com.example.dogapiconsumer.model.FactResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DogService {

    private final RestTemplate restTemplate;

    @Value("${dog.api.base-url}")
    private String baseUrl;

    @Value("${dog.api.facts-url}")
    private String factsUrl;

    @Value("${dog.api.random-image-url}")
    private String randomImageUrl;

    @Value("${http.dog.base-url}")
    private String httpDogBaseUrl;

    @Autowired
    public DogService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public DogApiListResponse getAllBreeds() {
        return restTemplate.getForObject(baseUrl, DogApiListResponse.class);
    }

    public DogApiSingleResponse getBreedById(String id) {
        String url = baseUrl + "/" + id;
        return restTemplate.getForObject(url, DogApiSingleResponse.class);
    }

    public FactResponse getFacts() {
        return restTemplate.getForObject(factsUrl, FactResponse.class);
    }

    public DogImageResponse getRandomDogImage() {
        DogCeoResponse ceoResponse = restTemplate.getForObject(randomImageUrl, DogCeoResponse.class);

        DogImageAttributes attributes = new DogImageAttributes();
        attributes.setUrl(ceoResponse.getMessage());

        DogImage dogImage = new DogImage();
        dogImage.setId("random");
        dogImage.setType("dog_image");
        dogImage.setAttributes(attributes);

        DogImageResponse response = new DogImageResponse();
        response.setData(dogImage);

        return response;
    }

    public DogImageResponse getHttpDogImage(int code) {
        DogImageAttributes attributes = new DogImageAttributes();
        attributes.setUrl(httpDogBaseUrl + code + ".jpg");

        DogImage dogImage = new DogImage();
        dogImage.setId(String.valueOf(code));
        dogImage.setType("http_dog_image");
        dogImage.setAttributes(attributes);

        DogImageResponse response = new DogImageResponse();
        response.setData(dogImage);

        return response;
    }
}
