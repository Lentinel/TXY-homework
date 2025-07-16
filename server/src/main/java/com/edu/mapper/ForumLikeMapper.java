package com.edu.mapper;

import com.edu.entity.ForumLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ForumLikeMapper {
    // 新增点赞
    int insert(ForumLike like);

    // 取消点赞
    int deleteByPostAndUser(@Param("postId") Long postId, @Param("userId") Long userId);
    int deleteByReplyAndUser(@Param("replyId") Long replyId, @Param("userId") Long userId);

    // 检查是否已点赞
    ForumLike selectByPostAndUser(@Param("postId") Long postId, @Param("userId") Long userId);
    ForumLike selectByReplyAndUser(@Param("replyId") Long replyId, @Param("userId") Long userId);
}