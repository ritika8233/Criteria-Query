package com.example.criteriaquerydemo.dto;

import lombok.Data;

import java.util.List;

@Data
public class SpecificationRequest {
    private List<SpecifiactionDto> list;
    private Operator operator;

    public enum Operator{
        AND, OR, BETWEEN, IN;
    }


}
