package com.edu.dto;

import lombok.Data;

@Data
public class UserPageQueryDTO {
    private int pageSize;
    private int page;

    private String nameSearch;


    //学生、教师，或者二者兼具
    private Integer roleSearch;
}
