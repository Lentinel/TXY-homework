package com.edu.entity;

import java.util.Date;

public class UserNotification {
    private long id;
    private Long userId;
    private Long notificationId;
    private Boolean isRead;
    private Date readTime;

    // 关联用户
    private User user;

    // 关联系统通知
    private SystemNotification notification;

}
