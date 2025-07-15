package com.edu.service.impl;

import com.edu.entity.ForumLike;
import com.edu.entity.ForumPost;
import com.edu.mapper.ForumLikeMapper;
import com.edu.mapper.ForumPostMapper;
import com.edu.mapper.ForumSectionMapper;
import com.edu.service.ForumPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ForumPostServiceImpl implements ForumPostService {
    @Autowired
    private ForumPostMapper postMapper;
    @Autowired
    private ForumSectionMapper sectionMapper;
    @Autowired
    private ForumLikeMapper likeMapper;

    @Transactional
    @Override
    public ForumPost createPost(ForumPost post) {
        // 保存帖子
        postMapper.insert(post);
        // 更新板块帖子计数
        sectionMapper.incrementPostCount(post.getSectionId());
        return post;
    }

    @Override
    public ForumPost updatePost(ForumPost post) {
        postMapper.update(post);
        return postMapper.selectById(post.getId());
    }

    @Transactional
    @Override
    public ForumPost getPostDetail(Long postId) {
        // 增加浏览量
        postMapper.incrementViewCount(postId);
        return postMapper.selectById(postId);
    }

    @Override
    public List<ForumPost> getPostsBySectionId(Long sectionId, Integer page, Integer size) {
        int offset = (page - 1) * size;
        return postMapper.selectBySectionId(sectionId, offset, size);
    }

    @Override
    public List<ForumPost> getHotPosts(Integer limit) {
        return postMapper.selectHotPosts(limit);
    }

    @Transactional
    @Override
    public boolean togglePostLike(Long postId, Long userId) {
        ForumLike like = likeMapper.selectByPostAndUser(postId, userId);
        if (like != null) {
            // 取消点赞
            likeMapper.deleteByPostAndUser(postId, userId);
            postMapper.decrementLikeCount(postId);
            return false;
        } else {
            // 新增点赞
            likeMapper.insert(ForumLike.builder()
                    .postId(postId)
                    .userId(userId)
                    .build());
            postMapper.incrementLikeCount(postId);
            return true;
        }
    }
}