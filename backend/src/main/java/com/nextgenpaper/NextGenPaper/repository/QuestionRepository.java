package com.nextgenpaper.NextGenPaper.repository;

import com.nextgenpaper.NextGenPaper.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, String> {
//    Get all the DISTINCT question group ID's for a single paper
//    @Query("SELECT DISTINCT q.questionGroupId FROM Question q WHERE q.questionPaper.id = :paperId ORDER BY q.qNo ASC , q.subQNo ASC")

    @Query("SELECT q.questionGroupId FROM Question q " +
            "WHERE q.questionPaper.id = :paperId " +
            "GROUP BY q.questionGroupId " +
            "ORDER BY MIN(q.qNo), MIN(q.subQNo)")
    List<String> findAllDistinctGroupIds(@Param("paperId") String paperId);


    Optional<Question> findByQuestionGroupIdAndVersionNo(String questionGroupId, int versionNo);

    Question findTopByQuestionGroupIdOrderByVersionNoDesc(String questionGroupId);


    @Query(value = """
    SELECT MAX(q.version_no) as max_version FROM question q
    WHERE q.question_paper_id = :paperId
""", nativeQuery = true)
    int getMaxVersion(String paperId);

    @Query(value= """
    SELECT MAX(q.version_no) as max_version FROM question q 
    WHERE q.question_paper_id = :paperId and q.question_group_id = :groupId
""", nativeQuery = true)
    public int getMaxVersionByGroupId(String paperId, String groupId);
}
