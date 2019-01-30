package com.game.staticcontest.Static.Contest.service;

import com.game.staticcontest.Static.Contest.dto.ContestDTO;
import com.game.staticcontest.Static.Contest.dto.ResponseDTO;
import com.game.staticcontest.Static.Contest.entity.Contest;
import com.game.staticcontest.Static.Contest.entity.ContestPlayArea;

import java.util.List;

public interface ContestPlayAreaService {

    List<ContestPlayArea> getContestPlayArea(String contestId, String userId);

    ContestPlayArea getContestPlayArea(String contestId, String questionId, String userId);

    String getMaximumQuestionSequence(String contestId, String userId);


    ContestPlayArea addContestPlayArea(ContestPlayArea contestPlayArea);

    int getNoOfSkips(String contestId, String userId);

}
