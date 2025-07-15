package com.edu.dto;

import lombok.Data;

@Data
public class CategoryPageQueryDTO {
    private int page;
    private int pageSize;
    private String name;
}
