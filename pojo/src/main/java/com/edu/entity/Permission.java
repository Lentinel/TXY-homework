package com.edu.entity;

import java.time.LocalDateTime;

public class Permission {
    private long id;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private String permissionCode;
    private String permissionName;
    private String description;
    private String module;
}
