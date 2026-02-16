package com.example.dogapiconsumer.model;

import lombok.Data;
import java.util.List;

@Data
public class FactResponse {
    private List<Fact> data;
}
