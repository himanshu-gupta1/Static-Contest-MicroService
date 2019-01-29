package com.game.staticcontest.Static.Contest.repository;

import com.game.staticcontest.Static.Contest.entity.ContestSubscribed;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContestSubscribedRepository extends CrudRepository<ContestSubscribed,String> {



    @Query(value = "SELECT * FROM contest_subscribed WHERE contest_id=?1 and user_id=?2",nativeQuery = true)
    ContestSubscribed getSubscribedContest(String contestId,String userId);



    @Query(value = "SELECT * FROM contest_subscribed WHERE user_id=?1",nativeQuery = true)
    List<ContestSubscribed> getAllContestByUserId(String userId);




}
