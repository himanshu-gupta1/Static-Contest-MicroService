package com.game.staticcontest.Static.Contest.service;

import com.game.staticcontest.Static.Contest.dto.ContestDTO;
import com.game.staticcontest.Static.Contest.dto.ResponseDTO;
import com.game.staticcontest.Static.Contest.entity.Contest;

import java.util.List;

public interface ContestService {

    ResponseDTO<ContestDTO> addContest(Contest contest);

    ResponseDTO<List<ContestDTO>> getAllContest();

    ResponseDTO<ContestDTO> getContest(String contestId,String userId);

}
