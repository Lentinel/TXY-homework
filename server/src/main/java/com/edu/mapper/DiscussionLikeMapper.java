package com.edu.mapper;

import com.edu.entity.DiscussionLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DiscussionLikeMapper {
    // 插入点赞记录
    int insert(DiscussionLike like);

    // 根据ID删除点赞记录
    int delete(Long id);

    // 根据回复ID和用户ID查询点赞记录
    DiscussionLike selectByReplyAndUser(@Param("replyId") Long replyId,
                                        @Param("userId") Long userId);
}