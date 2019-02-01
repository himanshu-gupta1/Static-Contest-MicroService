package com.game.staticcontest.Static.Contest.service;

import com.game.staticcontest.Static.Contest.dto.QuestionDetailDTO;
import com.game.staticcontest.Static.Contest.dto.ResponseDTO;
import com.game.staticcontest.Static.Contest.entity.ContestPlayArea;

import java.util.List;

public interface ContestPlayAreaService {



    ResponseDTO<QuestionDetailDTO> fetchNextQuestion(String contestId, String userId);

    ResponseDTO<Void> submitQuestion(String contestId, String questionId, String userId, String optionIds);


    ResponseDTO<Void> skipQuestion(String contestId, String questionId, String userId);



    List<ContestPlayArea> getContestPlayArea(String contestId, String userId);

    ContestPlayArea getContestPlayArea(String contestId, String questionId, String userId);

    Integer getMaximumQuestionSequence(String contestId, String userId);


    ContestPlayArea addContestPlayArea(ContestPlayArea contestPlayArea);

    int getNoOfSkips(String contestId, String userId);

    ResponseDTO<QuestionDetailDTO> getNextSkippedQuestion(String contestId, String userId);




}
