package com.game.staticcontest.Static.Contest.service;

import com.game.staticcontest.Static.Contest.dto.ContestDTO;
import com.game.staticcontest.Static.Contest.dto.ResponseDTO;
import com.game.staticcontest.Static.Contest.entity.Contest;
import com.game.staticcontest.Static.Contest.entity.ContestPlayArea;

public interface ContestPlayAreaService {

    ContestPlayArea getContestPlayArea(String contestId, String userId);

    String getMaximumQuestionSequence(String contestId, String userId);


    ContestPlayArea addContestPlayArea(ContestPlayArea contestPlayArea);





}
