package com.game.staticcontest.Static.Contest.repository;

import com.game.staticcontest.Static.Contest.entity.ContestPlayArea;
import com.game.staticcontest.Static.Contest.entity.ContestSubscribed;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContestPlayAreaRepository extends CrudRepository<ContestPlayArea,String> {

    @Query(value = "SELECT * FROM contest_play_area WHERE contest_id=?1 and user_id=?2",nativeQuery = true)
    List<ContestPlayArea> getContestPlayArea(String contestId, String userId);

    @Query(value = "SELECT * FROM contest_play_area WHERE contest_id=?1 and question_id=?2 and user_id=?3",nativeQuery = true)
    ContestPlayArea getContestPlayArea(String contestId, String questionId, String userId);

    @Query(value = "SELECT max(question_sequence) FROM contest_play_area WHERE contest_id=?1 and user_id=?2",nativeQuery = true)
    String getMaximumQuestionSequence(String contestId, String userId);

    @Query(value = "SELECT COUNT(*) FROM contest_play_area WHERE contest_id=?1 and user_id=?2 and skipped>0",nativeQuery = true)
    int getNoOfSkips(String contestId, String userId);


}
