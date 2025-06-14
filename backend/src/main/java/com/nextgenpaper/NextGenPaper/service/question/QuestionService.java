package com.nextgenpaper.NextGenPaper.service.question;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextgenpaper.NextGenPaper.entity.Question;
import com.nextgenpaper.NextGenPaper.exception.ResourceNotFoundException;
import com.nextgenpaper.NextGenPaper.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@AllArgsConstructor
public class QuestionService implements IQuestionService {

    private final QuestionRepository questionRepository;

//    Get question by Id
    @Override
    public Question getQuestion(String questionId) throws ResourceNotFoundException {
        return questionRepository.findById(questionId)
                .orElseThrow(()-> new ResourceNotFoundException("Question not found"));
    }

//    Update question by Id
    @Override
    public Question updateQuestion(String id, Question question) {
        Question oldQuestion = getQuestion(id);
        return questionRepository.save(question);
    }

//    Save question
    @Override
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

//  Generate the complete question paper using questionPaperId and version
    @Override
    public ArrayList<Question> getVersionOrFallback(String paperId, int targetVersion) {
        List<String> allGroupIds = questionRepository.findAllDistinctGroupIds(paperId);
        ArrayList<Question> questionList = new ArrayList<>();

        for (String groupId : allGroupIds) {
            Optional<Question> versionQ = questionRepository.findByQuestionGroupIdAndVersionNo(groupId, targetVersion);

            if (versionQ.isPresent()) {
                questionList.add(versionQ.get());
            } else {
                // Fallback to latest (max version)
                Question latestVersion = questionRepository.findTopByQuestionGroupIdOrderByVersionNoDesc(groupId);
                questionList.add(latestVersion);
            }
        }
        System.out.println(questionList);
        return questionList;
    }

//    Get the max version of any questionpaper by questionPaperId
    @Override
    public int getMaxVersion(String paperId) {
        return questionRepository.getMaxVersion(paperId);
    }

//    Get the max version of any questionpaper by questionPaperId and question groupid
    @Override
    public int getMaxVersionByGroupId(String paperId, String groupId) {
        return questionRepository.getMaxVersionByGroupId(paperId, groupId);
    }

//    Shuffle the questions to generate multiple sets
    @Override
    public ArrayList<Question> shuffleQuestions(ArrayList<Question> question){
        Random random = new Random();
        int[] swapCount = new int[1];
        while(swapCount[0] <= (question.size() / 2)){
            int index1 = random.nextInt(question.size()-1);
            int index2 = random.nextInt(question.size()-1);
            swapValues(question, index1, index2, swapCount);
        }
        Collections.sort(question);
        return question;
    }
    private void swapValues(ArrayList<Question> questionList, int index1, int index2, int[] swapCount){

        if( index1 == index2 || questionList.get(index1).getMarks() != questionList.get(index2).getMarks()) return;

        swapCount[0]++;
        Question q1 = questionList.get(index1);
        Question q2 = questionList.get(index2);
        Question temp = new Question();

//        Copy the question 1
        temp.setQuestion(q1.getQuestion());
        temp.setBloomLevel(q1.getBloomLevel());
        temp.setMarks(q1.getMarks());
        temp.setOptions(q1.getOptions());
        temp.setCorrectAnswer(q1.getCorrectAnswer());

//        Swap with question 1 with 2
        q1.setQuestion(q2.getQuestion());
        q1.setBloomLevel(q2.getBloomLevel());
        q1.setMarks(q2.getMarks());
        q1.setOptions(q2.getOptions());
        q1.setCorrectAnswer(q2.getCorrectAnswer());


//        Swap with question 2 wih temp/1
        q2.setQuestion(temp.getQuestion());
        q2.setBloomLevel(temp.getBloomLevel());
        q2.setMarks(temp.getMarks());
        q2.setOptions(temp.getOptions());
        q2.setCorrectAnswer(temp.getCorrectAnswer());

    }
}
