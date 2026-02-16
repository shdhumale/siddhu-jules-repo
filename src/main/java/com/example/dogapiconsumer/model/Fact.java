package com.example.dogapiconsumer.model;

import lombok.Data;

@Data
public class Fact {
    private String id;
    private String type;
    private FactAttributes attributes;
}
