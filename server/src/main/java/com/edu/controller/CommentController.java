package com.edu.controller;

import com.edu.result.Result;
import com.edu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/{courseId}/question")
    public Result startQuestion() {
        return null;
    }

    @DeleteMapping("/{courseId}/question/{questionId}")
    public Result deleteQuestion() {
        return null;
    }

    @GetMapping("/{courseId}/question")
    public Result questions() {
        return null;
    }
    @GetMapping("/{userId}/questions")
    public Result myQuestions(){return null;}
    @GetMapping("/{userId}/answers")
    public Result myAnswers(){return null;}
    @PostMapping("/{courseId}/question/{questionId}/answer")
    public Result answer() {
        return null;
    }

    @GetMapping("/{courseId}/question/{questionId}/answer")
    public Result answers() {
        return null;
    }

    @DeleteMapping("/{courseId}/question/{questionId}/answer/{answerId}")
    public Result deleteAnswer() {
        return null;
    }
    @PostMapping("/{courseId}/question/{questionId}/answer/{answerId}/like")
    public Result like(){return null;}


}