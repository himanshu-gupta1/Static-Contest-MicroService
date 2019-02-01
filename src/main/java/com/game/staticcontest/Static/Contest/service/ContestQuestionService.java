package com.game.staticcontest.Static.Contest.service;

import com.game.staticcontest.Static.Contest.dto.ResponseDTO;
import com.game.staticcontest.Static.Contest.entity.ContestQuestion;

public interface ContestQuestionService {

    ResponseDTO<Void> addQuestion(ContestQuestion contestQuestion);

    ContestQuestion findByQuestionSequence(int questionSequence, String contestId);

}
