package com.nextgenpaper.NextGenPaper.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Question  implements Comparable<Question> {

    @Id
    private String questionId;

//    UUID for tracking question changes
    private String questionGroupId;

    private int versionNo;
    @JsonProperty("qno")
    private int qNo;

    @JsonProperty("subqno")
    private int subQNo;

    private String question;

    @JsonProperty("bloomlevel")
    private String bloomLevel;

    private int marks;

    @ElementCollection
    private List<String> options; // For MCQs

    @JsonProperty("correctanswer")
    private String correctAnswer;

    @ManyToOne
    @JoinColumn(name = "question_paper_id")
    @JsonBackReference
    private QuestionPaper questionPaper;

    @Override
    public int compareTo(Question other) {
        // First compare qno
        int result = Integer.compare(this.qNo, other.qNo);
        if (result != 0) {
            return result;
        }
        // If qno is the same, compare subqno
        return Integer.compare(this.subQNo, other.subQNo);
    }
}
