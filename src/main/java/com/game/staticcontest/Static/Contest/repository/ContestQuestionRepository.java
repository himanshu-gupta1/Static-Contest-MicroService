package com.game.staticcontest.Static.Contest.repository;

import com.game.staticcontest.Static.Contest.entity.ContestQuestion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContestQuestionRepository extends CrudRepository<ContestQuestion, String> {

    ContestQuestion findByQuestionSequence(int questionSequence);
}
