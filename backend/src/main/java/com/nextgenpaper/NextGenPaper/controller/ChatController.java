package com.nextgenpaper.NextGenPaper.controller;

import com.nextgenpaper.NextGenPaper.dto.questionpaper.QuestionPaperRequest;
import com.nextgenpaper.NextGenPaper.entity.Question;
import com.nextgenpaper.NextGenPaper.entity.QuestionPaper;
import com.nextgenpaper.NextGenPaper.service.openrouter.OpenRouterService;
import com.nextgenpaper.NextGenPaper.service.pdfbox.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/generate")
public class ChatController {

    private final OpenRouterService openRouterService;
    private final PdfService pdfService;

//    Generate new Question paper
    @PostMapping(value = "/paper", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<QuestionPaper> ask(
            @RequestPart("request") QuestionPaperRequest request,
            @RequestPart("curriculumFile") MultipartFile curriculumFile,
            @RequestPart("questionFormat") MultipartFile questionFormat
    ) {
        request.setFileDetails(pdfService.pdfToText(curriculumFile));
        request.setQuestionFormat(pdfService.pdfToText(questionFormat));
        QuestionPaper response =  openRouterService.generateQuestionPaper(request);

        saveFiles(curriculumFile, response.getQuestionPaperId() + "_curriculum.pdf");
        saveFiles(questionFormat, response.getQuestionPaperId() + "_question_format.pdf");
        return ResponseEntity.ok(response);
    }
    private void saveFiles(MultipartFile file, String fileName)  {
        try {
            // 1. Save to resources/uploads/
            String tempDirPath = new File("src/main/resources/uploads").getAbsolutePath();
            File dir = new File(tempDirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File tempFile = new File(tempDirPath, fileName);
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(file.getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error processing PDF file: " + e.getMessage(), e);
        }
    }

//    Regenerate the question
    @PostMapping("/question/{questionPaperId}/{questionId}")
    public ResponseEntity<Question> regenerateQuestion(@PathVariable String questionId, @PathVariable String questionPaperId) {
        Question newQuestion = openRouterService.regenerateQuestion(questionId, questionPaperId);
        return ResponseEntity.ok(newQuestion);
    }
}
