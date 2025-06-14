package com.nextgenpaper.NextGenPaper.service.questionpaper;

import com.nextgenpaper.NextGenPaper.entity.QuestionPaper;
import com.nextgenpaper.NextGenPaper.exception.ResourceNotFoundException;

public interface IQuestionPaperService {
    public QuestionPaper convertStringToJSON(String questions);

    public QuestionPaper savePaper(QuestionPaper paper, String username);

    public QuestionPaper getQuestionPaper(String userId, String questionPaperId) throws ResourceNotFoundException;

    public void deleteQuestionPaper(String userId, String id);

    public boolean isExist(String userId, String questionPaperId);
}
