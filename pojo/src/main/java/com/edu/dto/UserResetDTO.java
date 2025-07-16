package com.edu.dto;

import lombok.Data;

@Data
public class UserResetDTO {
    private long id;
    private String passwordHash;

}
