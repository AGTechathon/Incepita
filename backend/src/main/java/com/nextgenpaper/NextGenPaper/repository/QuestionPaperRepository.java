package com.nextgenpaper.NextGenPaper.repository;


import com.nextgenpaper.NextGenPaper.entity.QuestionPaper;
import com.nextgenpaper.NextGenPaper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionPaperRepository extends JpaRepository<QuestionPaper, String> {

//    @Query(value = """
//
//""")
//    boolean existsByQuestionPaperIdAndUserId(String questionPaperId, String userId);

    @Query("SELECT COUNT(q) > 0 FROM QuestionPaper q WHERE q.questionPaperId = :questionPaperId AND q.user.username = :username")
    boolean existsByQuestionPaperIdAndUserId(@Param("questionPaperId") String questionPaperId,
                                             @Param("username") String username);

//    Optional<Object> findByQuestionPaperIdAndUser(String questionPaperId, User user);

//    Optional<QuestionPaper> findByQuestionPaperIdAndUserId(String questionPaperId, String username);

    @Query("SELECT q FROM QuestionPaper q WHERE q.id = :questionPaperId AND q.user.username = :username")
    Optional<QuestionPaper> findByQuestionPaperIdAndUsername(@Param("questionPaperId") String questionPaperId,
                                                             @Param("username") String username);

}
