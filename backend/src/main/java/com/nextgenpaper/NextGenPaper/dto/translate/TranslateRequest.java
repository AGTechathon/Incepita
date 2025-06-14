package com.nextgenpaper.NextGenPaper.dto.translate;

import lombok.Data;

@Data
public class TranslateRequest {
    String questionPaper;
    String from;
    String to;
}
