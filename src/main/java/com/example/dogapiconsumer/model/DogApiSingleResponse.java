package com.example.dogapiconsumer.model;

import lombok.Data;
import java.util.Map;

@Data
public class DogApiSingleResponse {
    private Breed data;
    private Map<String, Object> links;
}
