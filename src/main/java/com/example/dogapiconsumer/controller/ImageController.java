package com.example.dogapiconsumer.controller;

import com.example.dogapiconsumer.model.DogImageResponse;
import com.example.dogapiconsumer.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final DogService dogService;

    @Autowired
    public ImageController(DogService dogService) {
        this.dogService = dogService;
    }

    @GetMapping("/random")
    public DogImageResponse getRandomDogImage() {
        return dogService.getRandomDogImage();
    }

    @GetMapping("/http/{code}")
    public DogImageResponse getHttpDogImage(@PathVariable int code) {
        return dogService.getHttpDogImage(code);
    }
}
