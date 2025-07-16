package com.edu.mapper;

import com.edu.entity.UserExamRecord;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserExamRecordMapper {
    void insert(UserExamRecord record);
    void update(UserExamRecord record);
    UserExamRecord selectById(Long id);
    int countByUserIdAndExamId(@Param("userId") Long userId, @Param("examId") Long examId);
}