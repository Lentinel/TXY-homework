package com.edu.dto;

import lombok.Data;

@Data
public class ChapterPageQueryDTO {

    private int courseId;
    private int pageSize;
    private int page;
}
