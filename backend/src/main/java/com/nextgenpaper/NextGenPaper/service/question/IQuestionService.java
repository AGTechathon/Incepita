package com.nextgenpaper.NextGenPaper.service.question;

import com.nextgenpaper.NextGenPaper.entity.Question;
import com.nextgenpaper.NextGenPaper.exception.ResourceNotFoundException;

import java.util.ArrayList;

public interface IQuestionService {

    public Question getQuestion(String questionPaperId) throws ResourceNotFoundException;

    public Question updateQuestion(String id, Question question);

    public Question saveQuestion(Question question);

    public ArrayList<Question> getVersionOrFallback(String paperId, int targetVersion);

    public int getMaxVersion(String paperId);

    public int getMaxVersionByGroupId(String paperId, String groupId);

    public ArrayList<Question> shuffleQuestions(ArrayList<Question> question);

}
