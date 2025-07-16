package com.edu.dto;

import lombok.Data;

import java.util.Date;
@Data
public class UserRegisterDTO {
    private String username;
    private String passwordHash;
    private String comfirmPassword;
    private String email;
    private String phone;
    private String fullName;
    private String avatarUrl;
    private Integer gender;
    private Date birthday;
    private String role;
}
