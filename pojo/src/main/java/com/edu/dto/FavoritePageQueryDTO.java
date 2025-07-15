package com.edu.dto;

import lombok.Data;

@Data
public class FavoritePageQueryDTO {
    private int page;
    private int pageSize;
    private long userId;
}
