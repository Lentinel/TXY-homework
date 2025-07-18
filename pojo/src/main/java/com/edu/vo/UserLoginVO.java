package com.edu.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class UserLoginVO {

    private long id;
    private String name;
    private String username;
    private Integer role;
    private String token;
}
