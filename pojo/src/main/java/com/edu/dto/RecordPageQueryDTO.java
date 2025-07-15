package com.edu.dto;

import lombok.Data;

@Data
public class RecordPageQueryDTO {
    private int examId;
    private int page;
    private int pageSize;
}
