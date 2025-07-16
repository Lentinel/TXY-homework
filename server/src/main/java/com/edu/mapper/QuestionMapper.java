package com.edu.mapper;

import com.edu.entity.Question;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface QuestionMapper {

    /**
     * 根据ID查询试题
     */
    Question selectById(Long id);

    /**
     * 批量查询试题
     */
    List<Question> selectByIds(@Param("ids") List<Long> questionIds);

    /**
     * 新增试题
     */
    void insert(Question question);

    /**
     * 更新试题
     */
    void update(Question question);

    /**
     * 删除试题
     */
    void deleteById(Long id);

    /**
     * 根据条件分页查询试题（用于题库管理）
     */
    List<Question> selectByCondition(
            @Param("questionType") Integer questionType,
            @Param("difficulty") Integer difficulty,
            @Param("keyword") String keyword,
            @Param("creatorId") Long creatorId,
            @Param("offset") int offset,
            @Param("pageSize") int pageSize
    );

    /**
     * 统计符合条件的试题总数
     */
    int countByCondition(
            @Param("questionType") Integer questionType,
            @Param("difficulty") Integer difficulty,
            @Param("keyword") String keyword,
            @Param("creatorId") Long creatorId
    );

    void updateById(Question question);
}