package com.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private long id;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private String username;
    private String passwordHash;
    private String email;
    private String phone;
    private String name;
    private String avatarUrl;
    private Integer gender;
    private Date birthday;
    private Integer userStatus;
    private LocalDateTime lastLoginAt;

    // 用户角色关联
    private Integer role;
}
