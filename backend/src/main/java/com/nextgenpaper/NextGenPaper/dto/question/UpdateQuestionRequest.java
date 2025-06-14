package com.nextgenpaper.NextGenPaper.dto.question;

import lombok.Data;

@Data
public class UpdateQuestionRequest {
    private String questionId;
    private String questionPaperId;
    private String question;
    private String bloomLevel;
}
