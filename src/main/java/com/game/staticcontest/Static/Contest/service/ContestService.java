package com.game.staticcontest.Static.Contest.service;

import com.game.staticcontest.Static.Contest.dto.ResponseDTO;
import com.game.staticcontest.Static.Contest.entity.Contest;

public interface ContestService {

    ResponseDTO<Void> addContest(Contest contest);

}
