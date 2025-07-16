package com.edu.service.impl;

import com.edu.dto.QuestionCreateDTO;
import com.edu.dto.QuestionUpdateDTO;
import com.edu.entity.Question;
import com.edu.mapper.QuestionMapper;
import com.edu.service.QuestionService;
import com.edu.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private JwtUtil jwtUtil; // JWT 工具类

    @Autowired
    private HttpServletRequest request; // HTTP 请求
    @Override
    @Transactional

    public Question createQuestion(QuestionCreateDTO dto) {
        Question question = new Question();
        BeanUtils.copyProperties(dto, question);

        // 从请求头中获取 JWT 令牌并解析用户 ID
        String token = extractTokenFromRequest(request);
        Long userId = jwtUtil.getUserIdFromToken(token);

        question.setCreatorId(userId);
        question.setCreatedAt(LocalDateTime.now());
        question.setUpdatedAt(LocalDateTime.now());

        questionMapper.insert(question);
        return question;
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        throw new RuntimeException("未找到 JWT 令牌");
    }

    @Override
    @Transactional
    public Question updateQuestion(Long id, QuestionUpdateDTO dto) {
        Question question = questionMapper.selectById(id);
        if (question == null) {
            throw new RuntimeException("试题不存在");
        }

        // 更新允许修改的字段
        if (dto.getQuestionType() != null) {
            question.setQuestionType(dto.getQuestionType());
        }
        if (dto.getContent() != null) {
            question.setContent(dto.getContent());
        }
        if (dto.getOptions() != null) {
            question.setOptions(dto.getOptions());
        }
        if (dto.getAnswer() != null) {
            question.setAnswer(dto.getAnswer());
        }
        if (dto.getDifficulty() != null) {
            question.setDifficulty(dto.getDifficulty());
        }
        if (dto.getScore() != null) {
            question.setScore(dto.getScore());
        }
        question.setUpdatedAt(LocalDateTime.now());

        // 更新试题
        questionMapper.updateById(question);
        return question;
    }
}