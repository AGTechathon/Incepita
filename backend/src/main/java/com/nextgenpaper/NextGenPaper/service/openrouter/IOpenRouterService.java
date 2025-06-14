package com.nextgenpaper.NextGenPaper.service.openrouter;

import com.nextgenpaper.NextGenPaper.dto.questionpaper.QuestionPaperRequest;
import com.nextgenpaper.NextGenPaper.entity.Question;
import com.nextgenpaper.NextGenPaper.entity.QuestionPaper;

public interface IOpenRouterService {
    public QuestionPaper generateQuestionPaper(QuestionPaperRequest questionPaperRequest);

    public Question  regenerateQuestion(String question, String questionPaperId);
}
