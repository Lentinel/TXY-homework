CREATE TABLE `answer` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `question_id` bigint NOT NULL COMMENT '问题ID',
                          `user_id` bigint NOT NULL COMMENT '回答者ID',
                          `content` text NOT NULL COMMENT '回答内容',
                          `is_best` tinyint DEFAULT '0' COMMENT '是否最佳回答：0-否，1-是',
                          `like_count` int DEFAULT '0' COMMENT '点赞数',
                          `status` tinyint DEFAULT '1' COMMENT '状态：1-正常，2-已删除，3-已屏蔽',
                          `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                          `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          PRIMARY KEY (`id`),
                          KEY `user_id` (`user_id`),
                          KEY `idx_question` (`question_id`),
                          KEY `idx_best` (`is_best`),
                          CONSTRAINT `answer_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `question_answer` (`id`),
                          CONSTRAINT `answer_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `chapter` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `course_id` bigint NOT NULL COMMENT '所属课程ID',
                           `title` varchar(100) NOT NULL COMMENT '章节标题',
                           `description` text COMMENT '章节描述',
                           `sort_order` int NOT NULL COMMENT '章节顺序',
                           `content_type` tinyint DEFAULT '1' COMMENT '内容类型：1-文本，2-视频，3-文档',
                           `content` text COMMENT '章节内容（或内容链接）',
                           `estimated_time` int DEFAULT NULL COMMENT '预计学习时间（分钟）',
                           `is_required` tinyint DEFAULT '1' COMMENT '是否必修：0-选修，1-必修',
                           `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                           `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           PRIMARY KEY (`id`),
                           KEY `idx_course_order` (`course_id`,`sort_order`),
                           CONSTRAINT `chapter_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `chapter_exam` (
                                `chapter_id` bigint NOT NULL COMMENT '章节ID',
                                `exam_id` bigint NOT NULL COMMENT '考试ID',
                                `weight` decimal(5,2) DEFAULT '1.00' COMMENT '章节在考试中的权重',
                                PRIMARY KEY (`chapter_id`,`exam_id`),
                                KEY `idx_exam` (`exam_id`),
                                CONSTRAINT `chapter_exam_ibfk_1` FOREIGN KEY (`chapter_id`) REFERENCES `chapter` (`id`),
                                CONSTRAINT `chapter_exam_ibfk_2` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `chapter_resources` (
                                     `id` bigint NOT NULL,
                                     `chapter_id` bigint NOT NULL COMMENT '章节ID',
                                     `chapter_order` int DEFAULT '0' COMMENT '章节内排序',
                                     `prerequisite_id` bigint DEFAULT NULL COMMENT '前置资源ID',
                                     `is_quiz` tinyint DEFAULT '0' COMMENT '是否为测验',
                                     PRIMARY KEY (`id`),
                                     KEY `chapter_id` (`chapter_id`),
                                     KEY `prerequisite_id` (`prerequisite_id`),
                                     CONSTRAINT `chapter_resources_ibfk_1` FOREIGN KEY (`id`) REFERENCES `learning_resources` (`id`),
                                     CONSTRAINT `chapter_resources_ibfk_2` FOREIGN KEY (`chapter_id`) REFERENCES `chapter` (`id`),
                                     CONSTRAINT `chapter_resources_ibfk_3` FOREIGN KEY (`prerequisite_id`) REFERENCES `learning_resources` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `coupon` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `coupon_code` varchar(50) NOT NULL COMMENT '优惠券码',
                          `coupon_type` tinyint NOT NULL COMMENT '类型：1-满减券，2-折扣券，3-立减券',
                          `discount_value` decimal(10,2) NOT NULL COMMENT '优惠值（满减金额/折扣比例）',
                          `min_amount` decimal(10,2) DEFAULT '0.00' COMMENT '最低消费金额（满减券）',
                          `valid_start` datetime NOT NULL COMMENT '生效时间',
                          `valid_end` datetime NOT NULL COMMENT '失效时间',
                          `total_quantity` int NOT NULL COMMENT '总发行量',
                          `used_quantity` int DEFAULT '0' COMMENT '已使用数量',
                          `status` tinyint DEFAULT '1' COMMENT '状态：1-有效，2-已过期，3-已停用',
                          `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                          `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `coupon_code` (`coupon_code`),
                          KEY `idx_status` (`status`),
                          KEY `idx_valid_time` (`valid_start`,`valid_end`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `course` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `title` varchar(100) NOT NULL COMMENT '课程标题',
                          `description` text COMMENT '课程描述',
                          `instructor_id` bigint NOT NULL COMMENT '讲师ID',
                          `category_id` bigint DEFAULT NULL COMMENT '课程分类ID',
                          `duration` int DEFAULT NULL COMMENT '预计学习时长（小时）',
                          `difficulty` tinyint DEFAULT '2' COMMENT '难度：1-入门，2-中级，3-高级',
                          `status` tinyint DEFAULT '1' COMMENT '状态：1-未发布，2-已发布，3-已归档',
                          `price` decimal(10,2) DEFAULT '0.00' COMMENT '价格',
                          `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                          `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          `recommend` int DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY `idx_instructor` (`instructor_id`),
                          KEY `idx_status` (`status`),
                          KEY `idx_category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `course_category` (
                                   `id` bigint NOT NULL AUTO_INCREMENT,
                                   `parent_id` bigint DEFAULT NULL COMMENT '父分类ID',
                                   `name` varchar(50) NOT NULL COMMENT '分类名称',
                                   `description` varchar(255) DEFAULT NULL COMMENT '分类描述',
                                   `sort_order` int DEFAULT '0' COMMENT '排序',
                                   `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                   `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   PRIMARY KEY (`id`),
                                   KEY `idx_parent` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `course_discussion` (
                                     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                     `course_id` bigint DEFAULT NULL COMMENT '课程ID（NULL表示全局论坛）',
                                     `author_id` bigint NOT NULL COMMENT '发布者ID',
                                     `title` varchar(255) NOT NULL COMMENT '标题',
                                     `content` text NOT NULL COMMENT '内容',
                                     `type` tinyint NOT NULL DEFAULT '1' COMMENT '类型：1-课程问题，2-论坛帖子',
                                     `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：1-正常，2-已关闭，3-已置顶，4-已删除',
                                     `reply_count` int NOT NULL DEFAULT '0' COMMENT '回复数',
                                     `view_count` int NOT NULL DEFAULT '0' COMMENT '浏览量',
                                     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     PRIMARY KEY (`id`),
                                     KEY `idx_course_id` (`course_id`),
                                     KEY `idx_author_id` (`author_id`),
                                     KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='课程讨论/论坛主题表';

CREATE TABLE `course_chapter_progress` (
                                           `id` bigint NOT NULL AUTO_INCREMENT,
                                           `user_id` bigint NOT NULL COMMENT '学生ID',
                                           `chapter_id` bigint NOT NULL COMMENT '章节ID',
                                           `course_id` bigint NOT NULL COMMENT '课程ID（冗余字段，便于查询）',
                                           `status` tinyint NOT NULL DEFAULT '0' COMMENT '学习状态：0-未开始，1-进行中，2-已完成',
                                           `start_time` timestamp NULL DEFAULT NULL COMMENT '开始学习时间',
                                           `complete_time` timestamp NULL DEFAULT NULL COMMENT '完成学习时间',
                                           `study_duration` int DEFAULT '0' COMMENT '学习时长（秒）',
                                           `progress` decimal(5,2) DEFAULT '0.00' COMMENT '学习进度百分比',
                                           `is_passed` tinyint DEFAULT '0' COMMENT '是否通过：0-未通过，1-已通过',
                                           `score` decimal(5,2) DEFAULT NULL COMMENT '章节测试分数',
                                           `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                           `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                           PRIMARY KEY (`id`),
                                           UNIQUE KEY `uk_user_chapter` (`user_id`,`chapter_id`),
                                           KEY `idx_course_user` (`course_id`,`user_id`),
                                           KEY `idx_chapter` (`chapter_id`),
                                           CONSTRAINT `progress_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                                           CONSTRAINT `progress_ibfk_2` FOREIGN KEY (`chapter_id`) REFERENCES `chapter` (`id`),
                                           CONSTRAINT `progress_ibfk_3` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='学生章节学习进度表';

CREATE TABLE `course_enrollment` (
                                     `user_id` bigint NOT NULL COMMENT '用户ID',
                                     `course_id` bigint NOT NULL COMMENT '课程ID',
                                     `status` tinyint DEFAULT '1' COMMENT '状态：1-学习中，2-已完成，3-已放弃',
                                     `progress` decimal(5,2) DEFAULT '0.00' COMMENT '学习进度（0-100）',
                                     `start_time` datetime DEFAULT NULL COMMENT '开始时间',
                                     `completion_time` datetime DEFAULT NULL COMMENT '完成时间',
                                     `source` tinyint DEFAULT '1' COMMENT '来源：1-直接报名，2-学习路径，3-推荐',
                                     `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                     `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     PRIMARY KEY (`user_id`,`course_id`),
                                     KEY `idx_user_status` (`user_id`,`status`),
                                     KEY `idx_course_status` (`course_id`,`status`),
                                     CONSTRAINT `course_enrollment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                                     CONSTRAINT `course_enrollment_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `course_grade` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `user_id` bigint NOT NULL COMMENT '用户ID',
                                `course_id` bigint NOT NULL COMMENT '课程ID',
                                `final_score` decimal(5,2) DEFAULT '0.00' COMMENT '最终成绩',
                                `grade_breakdown` json DEFAULT NULL COMMENT '成绩组成明细',
                                `status` tinyint DEFAULT '1' COMMENT '状态：1-进行中，2-已完成',
                                `completion_time` datetime DEFAULT NULL COMMENT '完成时间',
                                `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `uniq_user_course` (`user_id`,`course_id`),
                                KEY `course_id` (`course_id`),
                                KEY `idx_user_course` (`user_id`,`course_id`),
                                CONSTRAINT `course_grade_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                                CONSTRAINT `course_grade_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `course_resources` (
                                    `id` bigint NOT NULL,
                                    `course_overview` text COMMENT '课程概述',
                                    `is_required` tinyint DEFAULT '0' COMMENT '是否必修',
                                    PRIMARY KEY (`id`),
                                    CONSTRAINT `course_resources_ibfk_1` FOREIGN KEY (`id`) REFERENCES `learning_resources` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `course_review` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `user_id` bigint NOT NULL COMMENT '用户ID',
                                 `course_id` bigint NOT NULL COMMENT '课程ID',
                                 `rating` tinyint NOT NULL COMMENT '评分（1-5）',
                                 `comment` text COMMENT '评论内容',
                                 `status` tinyint DEFAULT '1' COMMENT '状态：1-待审核，2-已发布，3-已拒绝',
                                 `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                 `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 PRIMARY KEY (`id`),
                                 KEY `user_id` (`user_id`),
                                 KEY `idx_course_rating` (`course_id`,`rating`),
                                 KEY `idx_status` (`status`),
                                 CONSTRAINT `course_review_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                                 CONSTRAINT `course_review_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `course_statistics` (
                                     `id` bigint NOT NULL AUTO_INCREMENT,
                                     `course_id` bigint NOT NULL COMMENT '课程ID',
                                     `student_count` int DEFAULT '0' COMMENT '学习人数',
                                     `completion_rate` decimal(5,2) DEFAULT '0.00' COMMENT '完成率',
                                     `average_score` decimal(5,2) DEFAULT '0.00' COMMENT '平均成绩',
                                     `review_count` int DEFAULT '0' COMMENT '评论数',
                                     `update_time` datetime NOT NULL COMMENT '统计更新时间',
                                     `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                     `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     PRIMARY KEY (`id`),
                                     UNIQUE KEY `uniq_course` (`course_id`),
                                     KEY `idx_update_time` (`update_time`),
                                     CONSTRAINT `course_statistics_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `discussion_reply` (
                                    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                    `discussion_id` bigint NOT NULL COMMENT '关联主题ID',
                                    `parent_id` bigint DEFAULT NULL COMMENT '父回复ID（用于嵌套回复）',
                                    `author_id` bigint NOT NULL COMMENT '回复者ID',
                                    `content` text NOT NULL COMMENT '回复内容',
                                    `is_best` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否最佳回答',
                                    `like_count` int NOT NULL DEFAULT '0' COMMENT '点赞数',
                                    `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：1-正常，2-已删除，3-已屏蔽',
                                    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    PRIMARY KEY (`id`),
                                    KEY `idx_discussion_id` (`discussion_id`),
                                    KEY `idx_author_id` (`author_id`),
                                    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='讨论回复表';

CREATE TABLE `discussion_like` (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                   `reply_id` bigint NOT NULL COMMENT '回复ID',
                                   `user_id` bigint NOT NULL COMMENT '用户ID',
                                   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `uk_reply_user` (`reply_id`,`user_id`),
                                   KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='讨论回复点赞表';

CREATE TABLE `exam` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `course_id` bigint NOT NULL COMMENT '所属课程ID',
                        `title` varchar(100) NOT NULL COMMENT '考试标题',
                        `description` text COMMENT '考试描述',
                        `duration` int NOT NULL COMMENT '考试时长（分钟）',
                        `passing_score` int DEFAULT '60' COMMENT '及格分数',
                        `is_final_exam` tinyint DEFAULT '0' COMMENT '是否为期末考试：0-否，1-是',
                        `coverage_type` tinyint DEFAULT '1' COMMENT '覆盖类型：1-单章节，2-多章节，3-整课程',
                        `status` tinyint DEFAULT '1' COMMENT '状态：1-草稿，2-已发布，3-已归档',
                        `max_attempts` int DEFAULT '3' COMMENT '最大尝试次数',
                        `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                        `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        PRIMARY KEY (`id`),
                        KEY `idx_course_status` (`course_id`,`status`),
                        CONSTRAINT `exam_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `exam_question` (
                                 `exam_id` bigint NOT NULL COMMENT '考试ID',
                                 `question_id` bigint NOT NULL COMMENT '试题ID',
                                 `sort_order` int NOT NULL COMMENT '题目顺序',
                                 `score` int NOT NULL COMMENT '本题分值',
                                 PRIMARY KEY (`exam_id`,`question_id`),
                                 KEY `question_id` (`question_id`),
                                 KEY `idx_exam_order` (`exam_id`,`sort_order`),
                                 CONSTRAINT `exam_question_ibfk_1` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`),
                                 CONSTRAINT `exam_question_ibfk_2` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `forum_section` (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '板块ID',
                                 `name` varchar(100) NOT NULL COMMENT '板块名称',
                                 `description` varchar(500) DEFAULT NULL COMMENT '板块描述',
                                 `icon` varchar(255) DEFAULT NULL COMMENT '板块图标',
                                 `sort_order` int NOT NULL DEFAULT '0' COMMENT '排序序号',
                                 `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：1-启用，2-禁用',
                                 `post_count` int NOT NULL DEFAULT '0' COMMENT '帖子数量',
                                 `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 PRIMARY KEY (`id`),
                                 KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='论坛板块表';

CREATE TABLE `forum_reply` (
                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT '回复ID',
                               `post_id` bigint NOT NULL COMMENT '帖子ID',
                               `parent_id` bigint DEFAULT NULL COMMENT '父回复ID（用于嵌套回复）',
                               `author_id` bigint NOT NULL COMMENT '回复者ID',
                               `content` text NOT NULL COMMENT '回复内容',
                               `like_count` int NOT NULL DEFAULT '0' COMMENT '点赞量',
                               `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：1-正常，2-删除，3-屏蔽',
                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               PRIMARY KEY (`id`),
                               KEY `idx_post_id` (`post_id`),
                               KEY `idx_parent_id` (`parent_id`),
                               KEY `idx_author_id` (`author_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='帖子回复表';

CREATE TABLE `forum_post` (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '帖子ID',
                              `section_id` bigint NOT NULL COMMENT '所属板块ID',
                              `title` varchar(255) NOT NULL COMMENT '帖子标题',
                              `content` text NOT NULL COMMENT '帖子内容',
                              `author_id` bigint NOT NULL COMMENT '作者ID',
                              `is_top` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否置顶',
                              `is_essence` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否精华',
                              `view_count` int NOT NULL DEFAULT '0' COMMENT '浏览量',
                              `reply_count` int NOT NULL DEFAULT '0' COMMENT '回复量',
                              `like_count` int NOT NULL DEFAULT '0' COMMENT '点赞量',
                              `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：1-正常，2-删除，3-屏蔽',
                              `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              PRIMARY KEY (`id`),
                              KEY `idx_section_id` (`section_id`),
                              KEY `idx_author_id` (`author_id`),
                              KEY `idx_create_time` (`create_time`),
                              KEY `idx_status_top` (`status`,`is_top`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='论坛帖子表';

CREATE TABLE `forum_like` (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
                              `post_id` bigint DEFAULT NULL COMMENT '帖子ID（与reply_id二选一）',
                              `reply_id` bigint DEFAULT NULL COMMENT '回复ID（与post_id二选一）',
                              `user_id` bigint NOT NULL COMMENT '用户ID',
                              `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `uk_post_user` (`post_id`,`user_id`) COMMENT '帖子点赞唯一约束',
                              UNIQUE KEY `uk_reply_user` (`reply_id`,`user_id`) COMMENT '回复点赞唯一约束'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='帖子/回复点赞表';

CREATE TABLE `learning_resources` (
                                      `id` bigint NOT NULL AUTO_INCREMENT,
                                      `resource_type` tinyint NOT NULL COMMENT '资源类型：1=文档，2=视频，3=音频，4=链接',
                                      `title` varchar(255) NOT NULL COMMENT '标题',
                                      `description` text COMMENT '描述',
                                      `url` varchar(255) NOT NULL COMMENT '资源URL',
                                      `size` bigint DEFAULT NULL COMMENT '资源大小（字节）',
                                      `duration` int DEFAULT NULL COMMENT '时长（分钟）',
                                      `sort_order` int DEFAULT '0' COMMENT '排序号',
                                      `uploader_id` bigint NOT NULL COMMENT '上传者ID',
                                      `course_id` bigint NOT NULL COMMENT '课程ID',
                                      `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      PRIMARY KEY (`id`),
                                      KEY `course_id` (`course_id`),
                                      CONSTRAINT `learning_resources_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `order` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `order_no` varchar(50) NOT NULL COMMENT '订单号',
                         `user_id` bigint NOT NULL COMMENT '用户ID',
                         `course_id` bigint NOT NULL COMMENT '课程ID',
                         `order_amount` decimal(10,2) NOT NULL COMMENT '订单金额',
                         `actual_amount` decimal(10,2) NOT NULL COMMENT '实际支付金额',
                         `discount_amount` decimal(10,2) DEFAULT '0.00' COMMENT '优惠金额',
                         `order_status` tinyint DEFAULT '1' COMMENT '订单状态：1-待支付，2-已支付，3-已退款，4-已取消',
                         `payment_method` tinyint DEFAULT '1' COMMENT '支付方式：1-微信，2-支付宝，3-余额',
                         `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
                         `refund_reason` varchar(255) DEFAULT NULL COMMENT '退款原因',
                         `refund_time` datetime DEFAULT NULL COMMENT '退款时间',
                         `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                         `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `order_no` (`order_no`),
                         KEY `course_id` (`course_id`),
                         KEY `idx_user_status` (`user_id`,`order_status`),
                         KEY `idx_order_no` (`order_no`),
                         CONSTRAINT `order_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                         CONSTRAINT `order_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `payment_record` (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `order_id` bigint NOT NULL COMMENT '订单ID',
                                  `transaction_id` varchar(100) DEFAULT NULL COMMENT '支付平台交易号',
                                  `payment_amount` decimal(10,2) NOT NULL COMMENT '支付金额',
                                  `payment_status` tinyint NOT NULL COMMENT '支付状态：1-支付中，2-支付成功，3-支付失败',
                                  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
                                  `payment_channel` tinyint NOT NULL COMMENT '支付渠道：1-微信支付，2-支付宝，3-银联',
                                  `error_msg` varchar(255) DEFAULT NULL COMMENT '支付失败原因',
                                  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                  PRIMARY KEY (`id`),
                                  KEY `idx_order` (`order_id`),
                                  KEY `idx_status` (`payment_status`),
                                  CONSTRAINT `payment_record_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `permission` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `permission_code` varchar(50) NOT NULL COMMENT '权限代码',
                              `permission_name` varchar(100) NOT NULL COMMENT '权限名称',
                              `description` varchar(255) DEFAULT NULL COMMENT '权限描述',
                              `module` varchar(50) DEFAULT NULL COMMENT '所属模块',
                              `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                              `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `permission_code` (`permission_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `question` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `question_type` tinyint NOT NULL COMMENT '题型：1-单选题，2-多选题，3-判断题，4-填空题，5-简答题',
                            `content` text NOT NULL COMMENT '题目内容',
                            `options` json DEFAULT NULL COMMENT '选项列表（选择题）：[{"id":1,"text":"选项A"},{"id":2,"text":"选项B"}]',
                            `answer` json NOT NULL COMMENT '答案（JSON格式）',
                            `difficulty` tinyint DEFAULT '2' COMMENT '难度：1-简单，2-中等，3-困难',
                            `score` int DEFAULT '1' COMMENT '默认分值',
                            `creator_id` bigint NOT NULL COMMENT '创建人ID',
                            `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                            `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            PRIMARY KEY (`id`),
                            KEY `idx_type_difficulty` (`question_type`,`difficulty`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `question_answer` (
                                   `id` bigint NOT NULL AUTO_INCREMENT,
                                   `title` varchar(100) NOT NULL COMMENT '问题标题',
                                   `content` text NOT NULL COMMENT '问题内容',
                                   `user_id` bigint NOT NULL COMMENT '提问者ID',
                                   `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                   `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   `exam_id` bigint DEFAULT NULL,
                                   `question_id` bigint DEFAULT NULL,
                                   `sortOrder` int DEFAULT NULL COMMENT '排序',
                                   `score` int DEFAULT NULL COMMENT '单题得分',
                                   PRIMARY KEY (`id`),
                                   KEY `user_id` (`user_id`),
                                   KEY `question_answer_ibfk_3` (`exam_id`),
                                   KEY `question_answer_ibfk_4` (`question_id`),
                                   CONSTRAINT `question_answer_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                                   CONSTRAINT `question_answer_ibfk_3` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`),
                                   CONSTRAINT `question_answer_ibfk_4` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `role` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `role_name` varchar(50) NOT NULL COMMENT '角色名称',
                        `description` varchar(255) DEFAULT NULL COMMENT '角色描述',
                        `is_system` tinyint DEFAULT '0' COMMENT '是否系统内置：0-否，1-是',
                        `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                        `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `role_name` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `role_permission` (
                                   `role_id` bigint NOT NULL,
                                   `permission_id` bigint NOT NULL,
                                   `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                   PRIMARY KEY (`role_id`,`permission_id`),
                                   KEY `idx_permission` (`permission_id`),
                                   CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
                                   CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `system_config` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `config_key` varchar(50) NOT NULL COMMENT '配置键',
                                 `config_value` text COMMENT '配置值',
                                 `config_type` tinyint NOT NULL COMMENT '类型：1-系统设置，2-支付设置，3-课程设置',
                                 `description` varchar(255) DEFAULT NULL COMMENT '配置描述',
                                 `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                 `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `config_key` (`config_key`),
                                 KEY `idx_type` (`config_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `system_notification` (
                                       `id` bigint NOT NULL AUTO_INCREMENT,
                                       `title` varchar(100) NOT NULL COMMENT '通知标题',
                                       `content` text NOT NULL COMMENT '通知内容',
                                       `notification_type` tinyint NOT NULL COMMENT '类型：1-系统公告，2-课程更新，3-考试提醒，4-互动通知',
                                       `send_time` datetime NOT NULL COMMENT '发送时间',
                                       `status` tinyint DEFAULT '1' COMMENT '状态：1-有效，2-已过期，3-已删除',
                                       `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                       `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                       PRIMARY KEY (`id`),
                                       KEY `idx_type_status` (`notification_type`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `username` varchar(50) NOT NULL COMMENT '用户名',
                        `password_hash` varchar(100) NOT NULL COMMENT '加密密码',
                        `email` varchar(100) NOT NULL COMMENT '邮箱',
                        `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
                        `full_name` varchar(50) DEFAULT NULL COMMENT '姓名',
                        `avatar_url` varchar(255) DEFAULT NULL COMMENT '头像URL',
                        `gender` tinyint DEFAULT '0' COMMENT '性别：0-未知，1-男，2-女',
                        `birthday` date DEFAULT NULL COMMENT '生日',
                        `user_status` tinyint DEFAULT '1' COMMENT '状态：1-正常，2-锁定，3-已注销',
                        `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                        `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        `last_login_at` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
                        `role` int DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `username` (`username`),
                        UNIQUE KEY `email` (`email`),
                        KEY `idx_username` (`username`),
                        KEY `idx_email` (`email`),
                        KEY `idx_status` (`user_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_chapter_progress` (
                                         `user_id` bigint NOT NULL COMMENT '用户ID',
                                         `chapter_id` bigint NOT NULL COMMENT '章节ID',
                                         `status` tinyint DEFAULT '1' COMMENT '状态：1-未开始，2-学习中，3-已完成',
                                         `completion_time` datetime DEFAULT NULL COMMENT '完成时间',
                                         `study_time` int DEFAULT '0' COMMENT '学习时长（分钟）',
                                         `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                         `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                         PRIMARY KEY (`user_id`,`chapter_id`),
                                         KEY `idx_chapter_status` (`chapter_id`,`status`),
                                         CONSTRAINT `user_chapter_progress_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                                         CONSTRAINT `user_chapter_progress_ibfk_2` FOREIGN KEY (`chapter_id`) REFERENCES `chapter` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_exam_record` (
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `user_id` bigint NOT NULL COMMENT '用户ID',
                                    `exam_id` bigint NOT NULL COMMENT '考试ID',
                                    `course_id` bigint NOT NULL COMMENT '所属课程ID',
                                    `score` decimal(5,2) DEFAULT '0.00' COMMENT '考试成绩',
                                    `is_passed` tinyint DEFAULT '0' COMMENT '是否通过：0-否，1-是',
                                    `status` tinyint DEFAULT '1' COMMENT '状态：1-进行中，2-已提交，3-已批改',
                                    `attempt_count` int DEFAULT '1' COMMENT '尝试次数',
                                    `start_time` datetime DEFAULT NULL COMMENT '开始时间',
                                    `submit_time` datetime DEFAULT NULL COMMENT '提交时间',
                                    `answers` json DEFAULT NULL COMMENT '用户答案：{questionId1: [optionId1, optionId2], ...}',
                                    `review` json DEFAULT NULL COMMENT '批改评语',
                                    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    PRIMARY KEY (`id`),
                                    KEY `course_id` (`course_id`),
                                    KEY `idx_user_exam` (`user_id`,`exam_id`),
                                    KEY `idx_exam_attempt` (`exam_id`,`attempt_count`),
                                    CONSTRAINT `user_exam_record_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                                    CONSTRAINT `user_exam_record_ibfk_2` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`),
                                    CONSTRAINT `user_exam_record_ibfk_3` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_learning_plan` (
                                      `id` bigint NOT NULL AUTO_INCREMENT,
                                      `user_id` bigint NOT NULL COMMENT '用户ID',
                                      `plan_name` varchar(100) NOT NULL COMMENT '计划名称',
                                      `description` text COMMENT '计划描述',
                                      `start_date` date NOT NULL COMMENT '开始日期',
                                      `end_date` date NOT NULL COMMENT '结束日期',
                                      `status` tinyint DEFAULT '1' COMMENT '状态：1-进行中，2-已完成，3-已取消',
                                      `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                      `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                      PRIMARY KEY (`id`),
                                      KEY `idx_user_status` (`user_id`,`status`),
                                      CONSTRAINT `user_learning_plan_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
