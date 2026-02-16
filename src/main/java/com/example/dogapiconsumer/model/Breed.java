package com.example.dogapiconsumer.model;

import lombok.Data;
import java.util.Map;

@Data
public class Breed {
    private String id;
    private String type;
    private BreedAttributes attributes;
    private Map<String, Object> relationships;
}
