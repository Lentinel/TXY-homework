package com.edu.service.impl;

import com.edu.entity.ForumSection;
import com.edu.mapper.ForumSectionMapper;
import com.edu.service.ForumSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumSectionServiceImpl implements ForumSectionService {
    @Autowired
    private ForumSectionMapper sectionMapper;

    @Override
    public List<ForumSection> getAllEnabledSections() {
        return sectionMapper.selectAllEnabled();
    }

    @Override
    public ForumSection getSectionById(Long id) {
        return sectionMapper.selectById(id);
    }
}