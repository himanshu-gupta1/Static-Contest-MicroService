package com.game.staticcontest.Static.Contest.service.implementation;

import com.game.staticcontest.Static.Contest.dto.ResponseDTO;
import com.game.staticcontest.Static.Contest.entity.Contest;
import com.game.staticcontest.Static.Contest.repository.ContestRepository;
import com.game.staticcontest.Static.Contest.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class ContestServiceImplementation implements ContestService {

    @Autowired
    private ContestRepository contestRepository;

    @Override
    public ResponseDTO<Void> addContest(Contest contest) {
        ResponseDTO<Void> responseDTO=new ResponseDTO<>();

        try{
            contestRepository.save(contest);

            responseDTO.setStatus("success");
            responseDTO.setErrorMessage("");

        }
        catch(Exception e){
            responseDTO.setStatus("failure");
            responseDTO.setErrorMessage(e.getMessage());
        }

        responseDTO.setResponse(null);

        return responseDTO;
    }
}
