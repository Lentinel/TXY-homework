package com.edu.mapper;

import com.edu.dto.ChapterDTO;
import com.edu.dto.ChapterPageQueryDTO;
import com.edu.entity.Chapter;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChapterMapper {
    Page<Chapter> pageQuery(ChapterPageQueryDTO chapterPageQueryDTO);

    Chapter query(ChapterDTO chapterDTO);

    void deleteByCourseId(long courseId);

    void update(Chapter chapter);

    void delete(long chapterId);

    void createChapter(Chapter chapter);


    List<Chapter> queryByCourseId(Long courseId);
}
