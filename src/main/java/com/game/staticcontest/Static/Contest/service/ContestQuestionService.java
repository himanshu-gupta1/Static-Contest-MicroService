package com.game.staticcontest.Static.Contest.service;

import com.game.staticcontest.Static.Contest.dto.QuestionDetailDTO;
import com.game.staticcontest.Static.Contest.dto.ResponseDTO;
import com.game.staticcontest.Static.Contest.entity.ContestQuestion;

import java.util.List;

public interface ContestQuestionService {

    ResponseDTO<Void> addQuestion(ContestQuestion contestQuestion);

    ContestQuestion findByQuestionSequence(int questionSequence,String contestId);

    ResponseDTO<Void> addQuestionToContest(String contestId,List<QuestionDetailDTO> questionDetailDTOList);


}
