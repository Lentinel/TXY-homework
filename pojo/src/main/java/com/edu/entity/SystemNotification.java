package com.edu.entity;

import java.util.Date;
import java.util.List;

public class SystemNotification {
    private long id;

        private String title;
        private String content;
        private Integer notificationType;
        private Date sendTime;
        private Integer status;

        // 关联用户通知
        private List<UserNotification> userNotifications;

        // Getters and Setters

}
