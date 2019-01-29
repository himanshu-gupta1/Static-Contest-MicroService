package com.game.staticcontest.Static.Contest.service;

import com.game.staticcontest.Static.Contest.dto.ContestSubscribedDTO;
import com.game.staticcontest.Static.Contest.dto.ResponseDTO;
import org.springframework.stereotype.Service;


public interface ContestSubscribedService {

    ResponseDTO<ContestSubscribedDTO> subscribe(String contestId, String userId);


    ResponseDTO<Void> finish(String contestId,String userId);
}
