package com.example.dogapiconsumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BreedAttributes {
    private String name;
    private String description;
    private LifeRange life;
    @JsonProperty("male_weight")
    private WeightRange maleWeight;
    @JsonProperty("female_weight")
    private WeightRange femaleWeight;
    private Boolean hypoallergenic;
}
