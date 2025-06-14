package com.nextgenpaper.NextGenPaper.dto.questionpaper;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;

@Getter
@Setter
public class QuestionPaperRequest {
    private String fileDetails;
    private String username;
    private Map<String, Integer> bloomsTaxonomy;
    private String questionFormat;
}
