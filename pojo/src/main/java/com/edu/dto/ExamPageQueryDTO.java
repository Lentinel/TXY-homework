package com.edu.dto;

import lombok.Data;

@Data
public class ExamPageQueryDTO {
    private int page;
    private int pageSize;
    private long courseId;

}
