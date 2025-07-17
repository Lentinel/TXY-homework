package com.edu.mapper;

import com.edu.entity.StudentLearningRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;
@Mapper
public interface StudentLearningRecordMapper  {

    // 查询用户的某视频记录
    StudentLearningRecord selectByUserAndVideo(@Param("userId") Long userId, @Param("videoId") Long videoId);

    // 查询用户的某章节下所有视频记录
    List<StudentLearningRecord> selectByUserAndChapter(@Param("userId") Long userId, @Param("chapterId") Long chapterId);

    // 查询用户的某章节记录（仅章节级，不含视频）
    StudentLearningRecord selectByUserAndChapterOnly(@Param("userId") Long userId, @Param("chapterId") Long chapterId);

    // 查询用户的某课程下所有章节记录
    List<StudentLearningRecord> selectByUserAndCourse(@Param("userId") Long userId, @Param("courseId") Long courseId);

    // 查询用户的某课程记录（仅课程级，不含章节）
    StudentLearningRecord selectByUserAndCourseOnly(@Param("userId") Long userId, @Param("courseId") Long courseId);

    // 插入或更新（根据userId+courseId+chapterId+videoId唯一索引）
    int insertOrUpdate(StudentLearningRecord record);

    Integer sumTotalLearnDuration(Long userId, Long courseId);
}