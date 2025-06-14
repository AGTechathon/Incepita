package com.nextgenpaper.NextGenPaper.controller;


import com.nextgenpaper.NextGenPaper.entity.Question;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    public Question updateQuestion(String id){
        return null;
    }
}
