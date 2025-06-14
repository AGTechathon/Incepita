package com.nextgenpaper.NextGenPaper.service.openrouter;

import com.nextgenpaper.NextGenPaper.dto.chat.ChatRequest;
import com.nextgenpaper.NextGenPaper.dto.chat.ChatResponse;
import com.nextgenpaper.NextGenPaper.dto.chat.Message;
import com.nextgenpaper.NextGenPaper.dto.questionpaper.QuestionPaperRequest;
import com.nextgenpaper.NextGenPaper.entity.Question;
import com.nextgenpaper.NextGenPaper.entity.QuestionPaper;
import com.nextgenpaper.NextGenPaper.repository.QuestionRepository;
import com.nextgenpaper.NextGenPaper.service.pdfbox.PdfService;
import com.nextgenpaper.NextGenPaper.service.question.QuestionService;
import com.nextgenpaper.NextGenPaper.service.questionpaper.QuestionPaperService;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OpenRouterService implements IOpenRouterService{

    private static final String OPENROUTER_URL = "https://openrouter.ai/api/v1/chat/completions";
    private static final String API_KEY = System.getenv("LLM_API_KEY"); // Replace with your key

    private final QuestionPaperService questionPaperService;
    private final QuestionService questionService;
    private final PdfService pdfService;
    private final QuestionRepository questionRepository;

//    Generate new question paper
    @Override
    public QuestionPaper generateQuestionPaper(QuestionPaperRequest questionPaperRequest) {
         RestTemplate restTemplate = new RestTemplate();
        // Prepare message
        String prompt = buildPrompt(questionPaperRequest);
        Message message = new Message("user", prompt);
        ChatRequest request = new ChatRequest("meta-llama/llama-4-maverick:free", Collections.singletonList(message));
        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        HttpEntity<ChatRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ChatResponse> response = restTemplate.exchange(
                OPENROUTER_URL,
                HttpMethod.POST,
                entity,
                ChatResponse.class
        );

        String questions = response.getBody().getChoices().get(0).getMessage().getContent();
        if(!questions.contains("qno")){
            return generateQuestionPaper(questionPaperRequest);
        }
        QuestionPaper paper = questionPaperService.convertStringToJSON(questions);
        paper = questionPaperService.savePaper(paper, questionPaperRequest.getUsername());
        return paper;
    }
    private String buildPrompt(QuestionPaperRequest request){
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are an expert in curriculum design, educational assessment, and question paper generation. Your task is to create a question paper based on the provided curriculum and a specific question paper format. The output must be a JSON array of question objects, adhering strictly to the specified schema.")
                .append("Curriculum : ")
                .append(request.getFileDetails())
                .append("\nQuestion Paper format : ")
                .append(request.getQuestionFormat())
                .append("Bloom's Taxonomy Distribution")
                .append(request.getBloomsTaxonomy())
                .append("""
                        Output JSON Schema:
                        Your output must be a JSON array where each element is a question object with the following properties. Adhere strictly to the property names and data types.
                        [
                          {
                            "qno": <integer>,               // Main question number (e.g., 1, 2, 3...)
                            "subqno": <integer>,            // Sub-question number within the main question (e.g., 1 for Q1.1, 2 for Q1.2)
                            "question": "<string>",         // The actual question text
                            "bloomlevel": "<string>",       // Bloom's Taxonomy level (e.g., "Remember", "Understand", "Apply", "Analyze", "Evaluate", "Create")
                            "marks": <integer>,             // Marks allocated for this sub-question
                            "options": ["<string>", "<string>", ...], // An array of strings for MCQ options. Empty array if not an MCQ.
                            "correctanswer": "<string>"     // The correct answer for an MCQ. Empty string if not an MCQ.
                          },
                          // ... more question objects
                        ]
                        """)
                .append("Output format: ")
                .append("JSON array of question objects, strictly adhering to the specified schema. No other text, explanations, or formatting outside the JSON block.");
        System.out.println(prompt);
        return prompt.toString();
    }

//    Regenerate any single question
    @Override
    public Question regenerateQuestion(String questionId, String questionPaperId) {

        Question question = questionService.getQuestion(questionId);

        RestTemplate restTemplate = new RestTemplate();
        // Prepare message
        String prompt = buildPrompt(question, questionPaperId);
        Message message = new Message("user", prompt);
        ChatRequest request = new ChatRequest("meta-llama/llama-4-maverick:free", Collections.singletonList(message));

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        HttpEntity<ChatRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ChatResponse> response = restTemplate.exchange(
                OPENROUTER_URL,
                HttpMethod.POST,
                entity,
                ChatResponse.class
        );

        String newQuestion = response.getBody().getChoices().get(0).getMessage().getContent();

        Question updatedQuestion = new Question();
        updatedQuestion.setQuestionId(UUID.randomUUID().toString());
        updatedQuestion.setQuestion(newQuestion);
        updatedQuestion.setQuestionPaper(question.getQuestionPaper());
        updatedQuestion.setQuestionGroupId(question.getQuestionGroupId());
        updatedQuestion.setBloomLevel(question.getBloomLevel());
        updatedQuestion.setMarks(question.getMarks());
        updatedQuestion.setOptions(question.getOptions());
        updatedQuestion.setVersionNo(
                questionRepository.getMaxVersionByGroupId(questionPaperId, question.getQuestionGroupId()) + 1
        );
        updatedQuestion.setQNo(question.getQNo());
        updatedQuestion.setSubQNo(question.getSubQNo());
        question = questionService.saveQuestion(updatedQuestion); // Save the question
        return question;
    }
    private String buildPrompt(Question question, String questionPaperId) {
        StringBuilder prompt = new StringBuilder();
//        on the same unit/topic as the original, ensuring
        prompt.append("""
                        You are given a question from a syllabus-based system. Your task is to generate a completely new question :
                        The topic/domain is consistent with the syllabus,
                        The Bloom’s taxonomy level is the same,
                        The marks are unchanged,
                        The new question tests a different concept or perspective, not just a rephrasing,
                        The output must be a plain text, without any other extra text
                        """)
                        .append("\n The Question is : ")
                        .append(question.getQuestion())
                .append("\nBlooms level is : ")
                .append(question.getBloomLevel())
                        .append("\n Syllabus : " )
                        .append(pdfService.pdfToText(questionPaperId+"_curriculum.pdf"))
                        .append("\nThe out put must be a single line text only, no other stuff should present rather the new generated question")
                        .append("Only one question should be generated.");

        System.out.println(prompt);
        return prompt.toString();
    }
}
