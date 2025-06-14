package com.nextgenpaper.NextGenPaper.controller;

import com.nextgenpaper.NextGenPaper.entity.Question;
import com.nextgenpaper.NextGenPaper.entity.QuestionPaper;
import com.nextgenpaper.NextGenPaper.exception.ResourceNotFoundException;
import com.nextgenpaper.NextGenPaper.service.pdfbox.PdfService;
import com.nextgenpaper.NextGenPaper.service.question.QuestionService;
import com.nextgenpaper.NextGenPaper.service.questionpaper.QuestionPaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/question-paper/")
public class QuestionPaperController {

    private final QuestionPaperService questionPaperService;
    private final QuestionService questionService;
    private final PdfService pdfService;

//    Get the paper by username, paperid, version
    @GetMapping("/get/{username}/{questionPaperId}/{targetVersion}")
    public ResponseEntity<QuestionPaper> getQuestionPaper(@PathVariable String username, @PathVariable String questionPaperId, @PathVariable(required = false) Integer targetVersion) {

        if(!questionPaperService.isExist(username, questionPaperId)){
            throw new ResourceNotFoundException("Paper not found");
        }

        if(targetVersion == null)
             targetVersion = questionService.getMaxVersion(questionPaperId);
        QuestionPaper paper = new QuestionPaper();
        paper.setQuestionPaperId(questionPaperId);
        paper.setQuestionList(
                questionService.getVersionOrFallback(questionPaperId, targetVersion)
        );
        return ResponseEntity.ok(paper);
    }

//    Shuffle paper by username, paperid, version
    @GetMapping("/shuffle/{username}/{questionPaperId}/{targetVersion}")
    public ResponseEntity<byte[]> getShuffledQuestionPaper(@PathVariable String username, @PathVariable String questionPaperId, @PathVariable(required = false) Integer targetVersion) {
        if(!questionPaperService.isExist(username, questionPaperId)){
            throw new ResourceNotFoundException("Paper not found");
        }
        if(targetVersion == null)
            targetVersion = questionService.getMaxVersion(questionPaperId);
        QuestionPaper paper = new QuestionPaper();
        paper.setQuestionPaperId(questionPaperId);
        ArrayList<Question> questionList = questionService.getVersionOrFallback(questionPaperId, targetVersion);
        questionList = questionService.shuffleQuestions(questionList);
        paper.setQuestionList(
                questionList
        );

        byte[] pdf = pdfService.generatePDF(questionList);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "questions.pdf"); // Triggers download
        headers.setContentLength(pdf.length);

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

//    Delete paper by username, paperid
    @PostMapping("/delete/{username}/{questionPaperId}")
    public ResponseEntity<String> deleteQuestionPaper( @PathVariable String username,  @PathVariable String questionPaperId) {
        questionPaperService.deleteQuestionPaper(username, questionPaperId);
        deleteFile(questionPaperId+"_curriculum.pdf");
        deleteFile(questionPaperId + "_question_format.pdf");
        return ResponseEntity.ok("Paper Deleted Successfully");
    }
    private void deleteFile(String fileName){
        String dir = new File("/src/main/resources/uploads/" + fileName).getAbsolutePath();
        File deleteFile = new File(dir);
        if(deleteFile.exists()){
            deleteFile.delete();
        }
    }
}
