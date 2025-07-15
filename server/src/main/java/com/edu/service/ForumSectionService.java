package com.edu.service;

import com.edu.entity.ForumSection;

import java.util.List;

public interface ForumSectionService {
    // 获取所有启用的板块
    List<ForumSection> getAllEnabledSections();

    // 根据ID获取板块详情
    ForumSection getSectionById(Long id);
}

// 实现类
