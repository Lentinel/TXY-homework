package com.edu.vo;

import com.edu.entity.Exam;
import com.edu.entity.ExamQuestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamWithQuestionsVO {
    private Exam exam;
    private List<ExamQuestion> questions;
    // 构造方法...
}
