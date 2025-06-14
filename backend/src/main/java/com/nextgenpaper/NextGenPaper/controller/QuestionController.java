package com.nextgenpaper.NextGenPaper.controller;


import com.nextgenpaper.NextGenPaper.dto.question.UpdateQuestionRequest;
import com.nextgenpaper.NextGenPaper.entity.Question;
import com.nextgenpaper.NextGenPaper.service.question.QuestionService;
import com.nextgenpaper.NextGenPaper.service.questionpaper.QuestionPaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class QuestionController {
    
    private final QuestionService questionService;
    private final QuestionPaperService questionPaperService;

    @PostMapping(path = "/update")
    public Question updateQuestion(UpdateQuestionRequest request){
        return questionService.updateQuestion(request);
    }
}
