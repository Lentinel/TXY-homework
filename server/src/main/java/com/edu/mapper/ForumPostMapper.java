package com.edu.mapper;

import com.edu.entity.ForumPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ForumPostMapper {
    // 发布帖子
    int insert(ForumPost post);

    // 更新帖子
    int update(ForumPost post);

    // 根据ID查询帖子
    ForumPost selectById(Long id);

    // 根据板块ID查询帖子列表
    List<ForumPost> selectBySectionId(
            @Param("sectionId") Long sectionId,
            @Param("offset") Integer offset,
            @Param("size") Integer size);

    // 查询热门帖子（按浏览量/回复量）
    List<ForumPost> selectHotPosts(@Param("limit") Integer limit);

    // 更新帖子统计（浏览量/回复量/点赞量）
    int incrementViewCount(Long postId);
    int incrementReplyCount(Long postId);
    int incrementLikeCount(Long postId);
    int decrementLikeCount(Long postId);
}