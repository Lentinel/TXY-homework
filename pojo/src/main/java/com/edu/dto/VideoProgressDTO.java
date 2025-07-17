package com.edu.dto;

import lombok.Data;

// DTO类
@Data
public class VideoProgressDTO {
    private Long userId;
    private Long videoId;
    private Integer progressSeconds; // 当前观看进度（秒）
    private Integer totalSeconds; // 视频总时长（秒）
}