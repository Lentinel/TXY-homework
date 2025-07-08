package com.edu.entity;

import java.util.Date;

public class UserChapterProgress {
        private long id;
        private Long userId;
        private Long chapterId;
        private Integer status;
        private Date completionTime;
        private Integer studyTime;

        // 关联用户
        private User user;

        // 关联章节
        private Chapter chapter;

        // Getters and Setters

}
