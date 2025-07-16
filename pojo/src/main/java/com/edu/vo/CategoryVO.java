package com.edu.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryVO {
    private long id;
    private long parent_id;
    private String name;
    private String parentName;
    private long description;

}
