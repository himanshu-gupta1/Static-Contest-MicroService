package com.game.staticcontest.Static.Contest.repository;

import com.game.staticcontest.Static.Contest.dto.QuestionDetailDTO;
import com.game.staticcontest.Static.Contest.entity.ContestQuestion;
import org.springframework.data.repository.CrudRepository;

public interface ContestQuestionRepository extends CrudRepository<ContestQuestion, String> {

    ContestQuestion findByQuestionSequence(String questionSequence);
}
