package com.nextgenpaper.NextGenPaper.service.questionpaper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextgenpaper.NextGenPaper.entity.Question;
import com.nextgenpaper.NextGenPaper.entity.QuestionPaper;
import com.nextgenpaper.NextGenPaper.exception.ResourceNotFoundException;
import com.nextgenpaper.NextGenPaper.repository.QuestionPaperRepository;
import com.nextgenpaper.NextGenPaper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuestionPaperService implements IQuestionPaperService {

    private final QuestionPaperRepository questionPaperRepository;
    private final UserRepository userRepository;

//    Convert AI response into JSON
    @Override
    public QuestionPaper convertStringToJSON(String questions){
        QuestionPaper paper = new QuestionPaper();
        ArrayList<String> stringQuestionList = getStrings(questions);
        List<Question> questionList = new ArrayList<>();
//      Mapping JSON to java
        try{
            for(String s : stringQuestionList){
                Question question = new Question();
                ObjectMapper objectMapper = new ObjectMapper();
                question = objectMapper.readValue(s, Question.class);
                question.setQuestionId(UUID.randomUUID().toString()); // Setting the question ID
                question.setQuestionGroupId(UUID.randomUUID().toString()); // Setting the question Group Id for versioning purpose
                question.setVersionNo(1); // Initial set the version to 1
                questionList.add(question);
            }
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        paper.setQuestionList(questionList);
        System.out.println(questions);
        return paper;
    }
    private static ArrayList<String> getStrings(String questions) {
        Stack<Character> stack = new Stack<>();
        ArrayList<String> stringQuestionList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        int i = 0;
        while(i < questions.length() && questions.charAt(i) != '{'){
            i++;
        }

        for(; i< questions.length(); i++){
            if(questions.charAt(i) == '{'){
                stack.push(questions.charAt(i));
            }
            if(questions.charAt(i) == '}'){
                sb.append(questions.charAt(i));
                stringQuestionList.add(sb.toString());
                sb = new StringBuilder();
                stack.pop();
            }
            else{
                if(!stack.isEmpty()) sb.append(questions.charAt(i));
            }
        }
        return stringQuestionList;
    }


//    Save Paper to DB
    @Override
    public QuestionPaper savePaper(QuestionPaper paper, String username){
        paper.setQuestionPaperId(UUID.randomUUID().toString());
        paper.setQuestionList(
                setQuestionPaperToQuestion(paper.getQuestionList(), paper)
        );
        paper.setUser(userRepository.findByUsername(username).orElse(null));
        return questionPaperRepository.save(paper);
    }
    private List<Question> setQuestionPaperToQuestion(List<Question> questionList, QuestionPaper paper){
        for(Question question : questionList){
            question.setQuestionPaper(paper);
        }
        return questionList;
    }


//    Get the question paper based on ID
    @Override
    public QuestionPaper getQuestionPaper(String username, String questionPaperId) throws ResourceNotFoundException {
        QuestionPaper paper =  questionPaperRepository.findByQuestionPaperIdAndUsername(questionPaperId, username)
                .orElseThrow(()-> new ResourceNotFoundException("Paper with id" + questionPaperId + " not found"));
        return paper;
    }


//  Delete question paper based on ID
    @Override
    public void deleteQuestionPaper(String userId, String questionPaperId) {
        QuestionPaper paper = getQuestionPaper(userId, questionPaperId);
        questionPaperRepository.delete(paper);
    }

//    Check whether paper esis
    @Override
    public boolean isExist(String userId, String questionPaperId) {
        return questionPaperRepository.existsByQuestionPaperIdAndUserId(questionPaperId, userId);
    }
}
