package com.edu.mapper;

import com.edu.entity.Video;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoMapper {
    List<Video> selectByChapterId(long id);
}
