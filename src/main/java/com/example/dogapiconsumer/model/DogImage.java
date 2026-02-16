package com.example.dogapiconsumer.model;

import lombok.Data;

@Data
public class DogImage {
    private String id;
    private String type;
    private DogImageAttributes attributes;
}
