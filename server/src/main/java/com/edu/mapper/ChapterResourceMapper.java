package com.edu.mapper;

import com.edu.entity.ChapterResource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChapterResourceMapper {
    // 插入章节资源
    void insert(ChapterResource resource);

    // 根据章节ID查询资源列表
    List<ChapterResource> selectByChapterId(Long chapterId);

    // 根据ID查询资源
    ChapterResource selectById(Long id);
}