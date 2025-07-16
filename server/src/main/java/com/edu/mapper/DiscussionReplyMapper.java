package com.edu.mapper;

import com.edu.entity.DiscussionReply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussionReplyMapper {
    // 插入回复
    int insert(DiscussionReply reply);

    // 更新回复
    int update(DiscussionReply reply);

    // 根据ID查询回复
    DiscussionReply selectById(Long id);

    // 根据讨论ID查询回复列表
    List<DiscussionReply> selectByDiscussionId(@Param("discussionId") Long discussionId,
                                               @Param("offset") Integer offset,
                                               @Param("size") Integer size);

    // 标记最佳回复
    int markBestReply(Long replyId);

    // 取消最佳回复
    int cancelBestReply(Long discussionId);

    // 增加点赞数
    int incrementLikeCount(Long replyId);

    // 减少点赞数
    int decrementLikeCount(Long replyId);

}