package com.edu.dto;

import lombok.Data;

import java.util.Date;
@Data
public class UserUpdateDTO {
    private long id;
    private String username;
    //private String passwordHash;
    private String email;
    private String phone;
    private String name;
    private String avatarUrl;
    private Integer gender;
    private Date birthday;
    //private Integer userStatus;
    //private Integer role;
}
