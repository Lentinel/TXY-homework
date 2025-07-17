package com.edu.service;

import com.edu.vo.CourseLearningVO;

public interface LearningRecordService {
    void updateVideoProgress(Long userId, Long videoId, Integer progressSeconds, Integer totalSeconds);

    CourseLearningVO getCourseLearningDetail(Long userId, Long courseId);
}
