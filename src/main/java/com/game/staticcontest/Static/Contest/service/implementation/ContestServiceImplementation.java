package com.game.staticcontest.Static.Contest.service.implementation;

import com.game.staticcontest.Static.Contest.dto.ContestDTO;
import com.game.staticcontest.Static.Contest.dto.ResponseDTO;
import com.game.staticcontest.Static.Contest.entity.Contest;
import com.game.staticcontest.Static.Contest.repository.ContestRepository;
import com.game.staticcontest.Static.Contest.service.ContestService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class ContestServiceImplementation implements ContestService {

    @Autowired
    private ContestRepository contestRepository;

    @Override
    public ResponseDTO<ContestDTO> addContest(Contest contest) {
        ResponseDTO<ContestDTO> responseDTO = new ResponseDTO<>();


        Contest contestAdded=contestRepository.save(contest);

        responseDTO.setStatus("success");
        responseDTO.setErrorMessage("");
        ContestDTO contestDTO=new ContestDTO();
        BeanUtils.copyProperties(contestAdded,contestDTO);

        responseDTO.setResponse(contestDTO);

        return responseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDTO<List<ContestDTO>> getAllContest() {
        List<ContestDTO> contestDTOList=new ArrayList<>();
        for(Contest contest:contestRepository.findAll())
        {
            ContestDTO contestDTO=new ContestDTO();
            BeanUtils.copyProperties(contest,contestDTO);
            contestDTOList.add(contestDTO);

        }

        ResponseDTO<List<ContestDTO>> responseDTO=new ResponseDTO<>();
        responseDTO.setStatus("success");
        responseDTO.setErrorMessage("");
        responseDTO.setResponse(contestDTOList);
        return responseDTO;

    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDTO<ContestDTO> getContest(String contestId) {
        ResponseDTO<ContestDTO> responseDTO=new ResponseDTO<>();
        responseDTO.setStatus("success");
        responseDTO.setErrorMessage("");
        Contest contest=contestRepository.findOne(contestId);
        ContestDTO contestDTO=new ContestDTO();
        BeanUtils.copyProperties(contest,contestDTO);
        responseDTO.setResponse(contestDTO);
        return responseDTO;
    }
}
