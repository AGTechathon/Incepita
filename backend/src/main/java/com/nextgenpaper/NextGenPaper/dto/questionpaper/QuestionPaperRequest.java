package com.nextgenpaper.NextGenPaper.dto.questionpaper;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class QuestionPaperRequest {
    private String fileDetails;
    private String username;
    private int noOfSets;
    private ArrayList<String> languages;
    private ArrayList<String> bloomsTaxonomy;
    private String questionFormat;
}
