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
                        `shuffle_questions` tinyint DEFAULT '1' COMMENT '是否随机排序题目',
                        `shuffle_options` tinyint DEFAULT '1' COMMENT '是否随机排序选项',
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

CREATE TABLE `forum` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `title` varchar(100) NOT NULL COMMENT '帖子标题',
                         `content` text NOT NULL COMMENT '帖子内容',
                         `user_id` bigint NOT NULL COMMENT '发布者ID',
                         `forum_type` tinyint DEFAULT '1' COMMENT '类型：1-讨论区，2-问答区，3-公告区',
                         `category_id` bigint DEFAULT NULL COMMENT '分类ID',
                         `view_count` int DEFAULT '0' COMMENT '浏览量',
                         `like_count` int DEFAULT '0' COMMENT '点赞数',
                         `comment_count` int DEFAULT '0' COMMENT '评论数',
                         `is_top` tinyint DEFAULT '0' COMMENT '是否置顶：0-否，1-是',
                         `is精华` tinyint DEFAULT '0' COMMENT '是否精华：0-否，1-是',
                         `status` tinyint DEFAULT '1' COMMENT '状态：1-正常，2-已删除，3-已屏蔽',
                         `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                         `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (`id`),
                         KEY `user_id` (`user_id`),
                         KEY `idx_type_status` (`forum_type`,`status`),
                         KEY `idx_view_like` (`view_count`,`like_count`),
                         CONSTRAINT `forum_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `forum_comment` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `forum_id` bigint NOT NULL COMMENT '帖子ID',
                                 `user_id` bigint NOT NULL COMMENT '评论者ID',
                                 `parent_id` bigint DEFAULT NULL COMMENT '父评论ID（用于回复）',
                                 `content` text NOT NULL COMMENT '评论内容',
                                 `like_count` int DEFAULT '0' COMMENT '点赞数',
                                 `status` tinyint DEFAULT '1' COMMENT '状态：1-正常，2-已删除，3-已屏蔽',
                                 `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                 `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 PRIMARY KEY (`id`),
                                 KEY `user_id` (`user_id`),
                                 KEY `idx_forum` (`forum_id`),
                                 KEY `idx_parent` (`parent_id`),
                                 CONSTRAINT `forum_comment_ibfk_1` FOREIGN KEY (`forum_id`) REFERENCES `forum` (`id`),
                                 CONSTRAINT `forum_comment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                                 CONSTRAINT `forum_comment_ibfk_3` FOREIGN KEY (`parent_id`) REFERENCES `forum_comment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `learning_path` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `title` varchar(100) NOT NULL COMMENT '路径标题',
                                 `description` text COMMENT '路径描述',
                                 `creator_id` bigint NOT NULL COMMENT '创建者ID',
                                 `cover_url` varchar(255) DEFAULT NULL COMMENT '封面图URL',
                                 `total_courses` int DEFAULT '0' COMMENT '包含课程数',
                                 `status` tinyint DEFAULT '1' COMMENT '状态：1-草稿，2-已发布，3-已归档',
                                 `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                 `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 PRIMARY KEY (`id`),
                                 KEY `creator_id` (`creator_id`),
                                 KEY `idx_status` (`status`),
                                 CONSTRAINT `learning_path_ibfk_1` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `learning_path_course` (
                                        `path_id` bigint NOT NULL COMMENT '路径ID',
                                        `course_id` bigint NOT NULL COMMENT '课程ID',
                                        `sort_order` int NOT NULL COMMENT '学习顺序',
                                        `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                        PRIMARY KEY (`path_id`,`course_id`),
                                        KEY `course_id` (`course_id`),
                                        KEY `idx_path_order` (`path_id`,`sort_order`),
                                        CONSTRAINT `learning_path_course_ibfk_1` FOREIGN KEY (`path_id`) REFERENCES `learning_path` (`id`),
                                        CONSTRAINT `learning_path_course_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `learning_plan_course` (
                                        `plan_id` bigint NOT NULL COMMENT '计划ID',
                                        `course_id` bigint NOT NULL COMMENT '课程ID',
                                        `target_date` date NOT NULL COMMENT '目标完成日期',
                                        `actual_date` date DEFAULT NULL COMMENT '实际完成日期',
                                        `status` tinyint DEFAULT '1' COMMENT '状态：1-未开始，2-进行中，3-已完成，4-已延期',
                                        `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                        `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                        PRIMARY KEY (`plan_id`,`course_id`),
                                        KEY `course_id` (`course_id`),
                                        KEY `idx_plan_status` (`plan_id`,`status`),
                                        CONSTRAINT `learning_plan_course_ibfk_1` FOREIGN KEY (`plan_id`) REFERENCES `user_learning_plan` (`id`),
                                        CONSTRAINT `learning_plan_course_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `learning_resource` (
                                     `id` bigint NOT NULL AUTO_INCREMENT,
                                     `course_id` bigint DEFAULT NULL COMMENT '关联课程ID',
                                     `chapter_id` bigint DEFAULT NULL COMMENT '关联章节ID',
                                     `title` varchar(100) NOT NULL COMMENT '资源标题',
                                     `description` varchar(255) DEFAULT NULL COMMENT '资源描述',
                                     `resource_type` tinyint NOT NULL COMMENT '资源类型：1-视频，2-文档，3-音频，4-链接',
                                     `url` varchar(255) NOT NULL COMMENT '资源链接',
                                     `size` int DEFAULT NULL COMMENT '资源大小（KB）',
                                     `duration` int DEFAULT NULL COMMENT '时长（分钟，视频/音频）',
                                     `sort_order` int DEFAULT '0' COMMENT '排序',
                                     `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                     `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     PRIMARY KEY (`id`),
                                     KEY `idx_course` (`course_id`),
                                     KEY `idx_chapter` (`chapter_id`),
                                     CONSTRAINT `learning_resource_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
                                     CONSTRAINT `learning_resource_ibfk_2` FOREIGN KEY (`chapter_id`) REFERENCES `chapter` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `message` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `sender_id` bigint NOT NULL COMMENT '发送者ID',
                           `receiver_id` bigint NOT NULL COMMENT '接收者ID',
                           `content` text NOT NULL COMMENT '消息内容',
                           `message_type` tinyint NOT NULL COMMENT '类型：1-私信，2-评论回复，3-问答回复，4-系统消息',
                           `is_read` tinyint DEFAULT '0' COMMENT '是否已读：0-未读，1-已读',
                           `send_time` datetime NOT NULL COMMENT '发送时间',
                           `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                           `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           PRIMARY KEY (`id`),
                           KEY `sender_id` (`sender_id`),
                           KEY `idx_receiver_read` (`receiver_id`,`is_read`),
                           KEY `idx_type` (`message_type`),
                           CONSTRAINT `message_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`),
                           CONSTRAINT `message_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`)
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
                                   `course_id` bigint DEFAULT NULL COMMENT '关联课程ID',
                                   `view_count` int DEFAULT '0' COMMENT '浏览量',
                                   `answer_count` int DEFAULT '0' COMMENT '回答数',
                                   `status` tinyint DEFAULT '1' COMMENT '状态：1-待回答，2-已回答，3-已关闭',
                                   `is_solved` tinyint DEFAULT '0' COMMENT '是否解决：0-否，1-是',
                                   `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                   `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                   PRIMARY KEY (`id`),
                                   KEY `user_id` (`user_id`),
                                   KEY `idx_status_solved` (`status`,`is_solved`),
                                   KEY `idx_course` (`course_id`),
                                   CONSTRAINT `question_answer_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                                   CONSTRAINT `question_answer_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`)
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

CREATE TABLE `user_career` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `user_id` bigint NOT NULL COMMENT '用户ID',
                               `company` varchar(100) DEFAULT NULL COMMENT '公司',
                               `position` varchar(100) DEFAULT NULL COMMENT '职位',
                               `industry` varchar(50) DEFAULT NULL COMMENT '行业',
                               `start_date` date DEFAULT NULL COMMENT '开始日期',
                               `end_date` date DEFAULT NULL COMMENT '结束日期',
                               `is_current` tinyint DEFAULT '0' COMMENT '是否当前职位：0-否，1-是',
                               `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                               `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               PRIMARY KEY (`id`),
                               KEY `idx_user` (`user_id`),
                               CONSTRAINT `user_career_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
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

CREATE TABLE `user_coupon` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `user_id` bigint NOT NULL COMMENT '用户ID',
                               `coupon_id` bigint NOT NULL COMMENT '优惠券ID',
                               `order_id` bigint DEFAULT NULL COMMENT '使用的订单ID',
                               `status` tinyint DEFAULT '1' COMMENT '状态：1-未使用，2-已使用，3-已过期',
                               `get_time` datetime NOT NULL COMMENT '领取时间',
                               `use_time` datetime DEFAULT NULL COMMENT '使用时间',
                               `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                               `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               PRIMARY KEY (`id`),
                               KEY `order_id` (`order_id`),
                               KEY `idx_user_status` (`user_id`,`status`),
                               KEY `idx_coupon` (`coupon_id`),
                               CONSTRAINT `user_coupon_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                               CONSTRAINT `user_coupon_ibfk_2` FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`),
                               CONSTRAINT `user_coupon_ibfk_3` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_education` (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `user_id` bigint NOT NULL COMMENT '用户ID',
                                  `school` varchar(100) DEFAULT NULL COMMENT '学校',
                                  `major` varchar(100) DEFAULT NULL COMMENT '专业',
                                  `degree` tinyint DEFAULT '0' COMMENT '学历：0-未知，1-高中，2-专科，3-本科，4-硕士，5-博士',
                                  `start_year` year DEFAULT NULL COMMENT '入学年份',
                                  `end_year` year DEFAULT NULL COMMENT '毕业年份',
                                  `is_current` tinyint DEFAULT '0' COMMENT '是否当前在读：0-否，1-是',
                                  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                  PRIMARY KEY (`id`),
                                  KEY `idx_user` (`user_id`),
                                  CONSTRAINT `user_education_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
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

CREATE TABLE `user_favorite` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `user_id` bigint NOT NULL COMMENT '用户ID',
                                 `course_id` bigint DEFAULT NULL COMMENT '收藏课程ID',
                                 `resource_id` bigint DEFAULT NULL COMMENT '收藏资源ID',
                                 `favorite_time` datetime NOT NULL COMMENT '收藏时间',
                                 `is_deleted` tinyint DEFAULT '0' COMMENT '是否删除：0-否，1-是',
                                 `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                 `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 PRIMARY KEY (`id`),
                                 KEY `idx_user_course` (`user_id`,`course_id`,`is_deleted`),
                                 KEY `idx_user_resource` (`user_id`,`resource_id`,`is_deleted`),
                                 KEY `course_id` (`course_id`),
                                 KEY `resource_id` (`resource_id`),
                                 CONSTRAINT `user_favorite_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                                 CONSTRAINT `user_favorite_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
                                 CONSTRAINT `user_favorite_ibfk_3` FOREIGN KEY (`resource_id`) REFERENCES `learning_resource` (`id`),
                                 CONSTRAINT `user_favorite_chk_1` CHECK (((`course_id` is not null) or (`resource_id` is not null)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_learning_log` (
                                     `id` bigint NOT NULL AUTO_INCREMENT,
                                     `user_id` bigint NOT NULL COMMENT '用户ID',
                                     `course_id` bigint DEFAULT NULL COMMENT '课程ID',
                                     `chapter_id` bigint DEFAULT NULL COMMENT '章节ID',
                                     `action_type` tinyint NOT NULL COMMENT '行为类型：1-开始学习，2-完成学习，3-暂停，4-提交作业',
                                     `action_time` datetime NOT NULL COMMENT '行为时间',
                                     `duration` int DEFAULT '0' COMMENT '时长（秒）',
                                     `progress` decimal(5,2) DEFAULT '0.00' COMMENT '进度',
                                     `extra_info` json DEFAULT NULL COMMENT '额外信息',
                                     `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                     PRIMARY KEY (`id`),
                                     KEY `idx_user_time` (`user_id`,`action_time`),
                                     KEY `idx_course` (`course_id`),
                                     KEY `idx_chapter` (`chapter_id`)
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

CREATE TABLE `user_login_log` (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `user_id` bigint NOT NULL COMMENT '用户ID',
                                  `login_time` datetime NOT NULL COMMENT '登录时间',
                                  `ip_address` varchar(50) DEFAULT NULL COMMENT '登录IP',
                                  `user_agent` varchar(255) DEFAULT NULL COMMENT '用户代理',
                                  `login_method` tinyint DEFAULT '1' COMMENT '登录方式：1-密码，2-微信，3-QQ，4-支付宝',
                                  `is_success` tinyint DEFAULT '1' COMMENT '是否成功：0-失败，1-成功',
                                  `failure_reason` varchar(100) DEFAULT NULL COMMENT '失败原因',
                                  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                  PRIMARY KEY (`id`),
                                  KEY `idx_user_time` (`user_id`,`login_time`),
                                  KEY `idx_ip` (`ip_address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_notification` (
                                     `id` bigint NOT NULL AUTO_INCREMENT,
                                     `user_id` bigint NOT NULL COMMENT '用户ID',
                                     `notification_id` bigint NOT NULL COMMENT '系统通知ID',
                                     `is_read` tinyint DEFAULT '0' COMMENT '是否已读：0-未读，1-已读',
                                     `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
                                     `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                     `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     PRIMARY KEY (`id`),
                                     KEY `notification_id` (`notification_id`),
                                     KEY `idx_user_read` (`user_id`,`is_read`),
                                     CONSTRAINT `user_notification_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                                     CONSTRAINT `user_notification_ibfk_2` FOREIGN KEY (`notification_id`) REFERENCES `system_notification` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_role` (
                             `user_id` bigint NOT NULL,
                             `role_id` bigint NOT NULL,
                             `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                             PRIMARY KEY (`user_id`,`role_id`),
                             KEY `idx_role` (`role_id`),
                             CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                             CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_token` (
                              `id` bigint NOT NULL AUTO_INCREMENT,
                              `user_id` bigint NOT NULL COMMENT '用户ID',
                              `token` varchar(100) NOT NULL COMMENT '令牌',
                              `token_type` tinyint DEFAULT '1' COMMENT '令牌类型：1-访问令牌，2-刷新令牌',
                              `expires_at` datetime NOT NULL COMMENT '过期时间',
                              `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                              `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `token` (`token`),
                              KEY `idx_user_token` (`user_id`,`token`),
                              KEY `idx_expires` (`expires_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
