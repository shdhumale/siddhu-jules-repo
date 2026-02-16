package com.example.dogapiconsumer.service;

import com.example.dogapiconsumer.model.DogApiListResponse;
import com.example.dogapiconsumer.model.DogApiSingleResponse;
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
}
