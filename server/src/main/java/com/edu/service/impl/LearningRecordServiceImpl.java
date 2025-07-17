package com.edu.service.impl;

import com.edu.entity.Chapter;
import com.edu.entity.Course;
import com.edu.entity.StudentLearningRecord;
import com.edu.entity.Video;
import com.edu.mapper.ChapterMapper;
import com.edu.mapper.CourseMapper;
import com.edu.mapper.StudentLearningRecordMapper;
import com.edu.mapper.VideoMapper;
import com.edu.service.LearningRecordService;
import com.edu.vo.ChapterLearningVO;
import com.edu.vo.CourseLearningVO;
import com.edu.vo.VideoLearningVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LearningRecordServiceImpl implements LearningRecordService {

    @Autowired
    private StudentLearningRecordMapper recordMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private CourseMapper courseMapper;
    @Override
    public void updateVideoProgress(Long userId, Long videoId, Integer progressSeconds, Integer totalSeconds) {

    }

    /**
     * 获取课程学习详情（包含章节和视频进度）
     */
    @Override
    public CourseLearningVO getCourseLearningDetail(Long userId, Long courseId) {
        // 1. 查询课程基本信息
        Course course = courseMapper.query(courseId);
        if (course == null) {
            throw new RuntimeException("课程不存在");
        }

        // 2. 查询课程下的所有章节
        List<Chapter> chapters = chapterMapper.queryByCourseId(courseId);
        if (chapters.isEmpty()) {
            return new CourseLearningVO();
        }

        // 3. 构建返回结果
        CourseLearningVO result = new CourseLearningVO();
        result.setCourseId(courseId);
        result.setCourseName(course.getTitle());

        // 4. 计算课程完成率
        int totalChapters = chapters.size();
        int completedChapters = 0;

        // 5. 处理每个章节的学习记录
        List<ChapterLearningVO> chapterVOs = new ArrayList<>();
        for (Chapter chapter : chapters) {
            ChapterLearningVO chapterVO = new ChapterLearningVO();
            chapterVO.setChapterId(chapter.getId());
            chapterVO.setChapterName(chapter.getTitle());

            // 6. 查询章节完成状态
            StudentLearningRecord chapterRecord = recordMapper.selectByUserAndChapterOnly(userId, chapter.getId());
            if (chapterRecord != null && chapterRecord.getIsCompleted() == 1) {
                chapterVO.setIsCompleted(true);
                completedChapters++;
            } else {
                chapterVO.setIsCompleted(false);
            }

            // 7. 查询章节下的所有视频及学习记录
            List<Video> videos = videoMapper.selectByChapterId(chapter.getId());
            List<VideoLearningVO> videoVOs = new ArrayList<>();

            for (Video video : videos) {
                VideoLearningVO videoVO = convertToVideoVO(userId, video);
                videoVOs.add(videoVO);
            }

            chapterVO.setVideos(videoVOs);
            chapterVOs.add(chapterVO);
        }

        // 8. 设置课程完成率
        result.setCompletionRate(totalChapters > 0 ? (double) completedChapters / totalChapters : 0);
        result.setChapters(chapterVOs);

        // 9. 计算总学习时长
        Integer totalDuration = recordMapper.sumTotalLearnDuration(userId, courseId);
        result.setTotalLearnDurationStr(formatDuration(totalDuration));

        return result;
    }

    /**
     * 将 Video 和学习记录转换为 VideoLearningVO
     */
    private VideoLearningVO convertToVideoVO(Long userId, Video video) {
        VideoLearningVO vo = new VideoLearningVO();
        vo.setVideoId(video.getId());
        vo.setVideoName(video.getTitle());
        vo.setTotalSeconds(video.getDuration());
        vo.setVideoUrl(video.getUrl());

        // 查询学习记录
        StudentLearningRecord record = recordMapper.selectByUserAndVideo(userId, video.getId());
        if (record != null) {
            vo.setProgressSeconds(record.getProgressSeconds());
            vo.setProgressPercent(record.getTotalSeconds() > 0 ?
                    (double) record.getProgressSeconds() / record.getTotalSeconds() * 100 : 0);
            vo.setIsCompleted(record.getIsCompleted() == 1);
            vo.setLastLearnTime(record.getLastLearnTime());
            vo.setTotalLearnDurationStr(formatDuration(record.getTotalLearnDuration()));
        } else {
            // 没有学习记录，设置默认值
            vo.setProgressSeconds(0);
            vo.setProgressPercent(0.0);
            vo.setIsCompleted(false);
            vo.setTotalLearnDurationStr("00:00:00");
        }

        return vo;
    }

    /**
     * 格式化时长（秒 → HH:MM:SS）
     */
    private String formatDuration(Integer seconds) {
        if (seconds == null || seconds <= 0) {
            return "00:00:00";
        }

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
}