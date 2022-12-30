package com.example.criteriaquerydemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecifiactionDto {
    private String column;
    private String value;
    private Operation operation;
    private String joinTable;

    public enum Operation{
        EQUAL, LIKE, IN, BETWEEN, GREATER_THAN, LESS_THAN, JOIN;
    }

}
