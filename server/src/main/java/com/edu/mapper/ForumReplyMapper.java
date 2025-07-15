package com.edu.mapper;

import com.edu.entity.ForumReply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ForumReplyMapper {
    // 新增回复
    int insert(ForumReply reply);

    // 根据帖子ID查询回复列表
    List<ForumReply> selectByPostId(
            @Param("postId") Long postId,
            @Param("offset") Integer offset,
            @Param("size") Integer size);

    // 查询帖子的最后一条回复
    ForumReply selectLastByPostId(Long postId);

    // 更新回复点赞数
    int incrementLikeCount(Long replyId);
    int decrementLikeCount(Long replyId);
}