package com.example.dogapiconsumer.controller;

import com.example.dogapiconsumer.model.DogApiListResponse;
import com.example.dogapiconsumer.model.DogApiSingleResponse;
import com.example.dogapiconsumer.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/breeds")
public class DogController {

    private final DogService dogService;

    @Autowired
    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @GetMapping
    public DogApiListResponse getAllBreeds() {
        return dogService.getAllBreeds();
    }

    @GetMapping("/{id}")
    public DogApiSingleResponse getBreedById(@PathVariable String id) {
        return dogService.getBreedById(id);
    }
}
