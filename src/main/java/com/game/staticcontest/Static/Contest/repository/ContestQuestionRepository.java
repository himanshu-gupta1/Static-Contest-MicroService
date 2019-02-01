package com.game.staticcontest.Static.Contest.repository;

import com.game.staticcontest.Static.Contest.entity.ContestQuestion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContestQuestionRepository extends CrudRepository<ContestQuestion, String> {





    @Query(value = "SELECT * FROM contest_question WHERE question_sequence=?1 and contest_id=?2",nativeQuery = true)
    ContestQuestion findContestQuestionByQuestionSequenceAndContestId(int questionSequence,String contestId);
}
