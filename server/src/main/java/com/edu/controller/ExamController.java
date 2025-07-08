package com.edu.controller;

import com.edu.annotation.AutoFill;
import com.edu.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ExamController {
    @Autowired
    private ExamService examService;

}
