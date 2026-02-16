package com.example.dogapiconsumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LifeRange {
    private Integer max;
    private Integer min;
}
