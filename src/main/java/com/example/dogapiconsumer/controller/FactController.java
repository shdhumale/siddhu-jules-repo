package com.example.dogapiconsumer.controller;

import com.example.dogapiconsumer.model.FactResponse;
import com.example.dogapiconsumer.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/facts")
public class FactController {

    private final DogService dogService;

    @Autowired
    public FactController(DogService dogService) {
        this.dogService = dogService;
    }

    @GetMapping
    public FactResponse getFacts() {
        return dogService.getFacts();
    }
}
