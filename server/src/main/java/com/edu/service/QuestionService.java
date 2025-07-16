package com.edu.service;

import com.edu.dto.QuestionCreateDTO;
import com.edu.dto.QuestionUpdateDTO;
import com.edu.entity.Question;

public interface QuestionService {

    Question createQuestion(QuestionCreateDTO dto);

    Question updateQuestion(Long id, QuestionUpdateDTO dto);
}
