package com.edu.controller.teacher;

import com.edu.dto.QuestionCreateDTO;
import com.edu.dto.QuestionUpdateDTO;
import com.edu.entity.Question;
import com.edu.result.Result;
import com.edu.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    /**
     * 创建试题
     */
    @PostMapping
    public Result<Question> createQuestion(@RequestBody QuestionCreateDTO dto) {
        Question question = questionService.createQuestion(dto);
        return Result.success(question);
    }

    /**
     * 更新试题
     */
    @PutMapping("/{id}")
    public Result<Question> updateQuestion(
            @PathVariable Long id,
            @RequestBody QuestionUpdateDTO dto
    ) {
        Question question = questionService.updateQuestion(id, dto);
        return Result.success(question);
    }
}