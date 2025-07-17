package com.edu.controller.student;

import com.edu.dto.VideoProgressDTO;
import com.edu.result.Result;
import com.edu.service.LearningRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/learning")
public class LearningRecordController {

    @Autowired
    private LearningRecordService learningRecordService;

    /**
     * 更新视频学习进度
     */
    @PostMapping("/video/progress")
    public Result<Void> updateVideoProgress(@RequestBody VideoProgressDTO dto) {
        learningRecordService.updateVideoProgress(
                dto.getUserId(),
                dto.getVideoId(),
                dto.getProgressSeconds(),
                dto.getTotalSeconds()
        );
        return Result.success();
    }


}