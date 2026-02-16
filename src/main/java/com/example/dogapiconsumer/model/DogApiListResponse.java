package com.example.dogapiconsumer.model;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class DogApiListResponse {
    private List<Breed> data;
    private Map<String, Object> links;
    private Map<String, Object> meta;
}
