package com.edu.mapper;

import com.edu.entity.ForumSection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ForumSectionMapper {
    // 查询所有启用的板块
    List<ForumSection> selectAllEnabled();

    // 根据ID查询板块
    ForumSection selectById(Long id);

    // 更新板块帖子数量
    int incrementPostCount(Long sectionId);
}