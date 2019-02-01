package com.game.staticcontest.Static.Contest.service.implementation;

import com.contest.notificationProducer.dto.Header;
import com.contest.notificationProducer.notificationEnum.NotificationMedium;
import com.contest.notificationProducer.notificationEnum.NotificationType;
import com.contest.notificationProducer.producer.ContestProducer;
import com.game.staticcontest.Static.Contest.dto.ContestDTO;
import com.game.staticcontest.Static.Contest.dto.QuestionDetailDTO;
import com.game.staticcontest.Static.Contest.dto.ResponseDTO;
import com.game.staticcontest.Static.Contest.entity.Contest;
import com.game.staticcontest.Static.Contest.entity.ContestQuestion;
import com.game.staticcontest.Static.Contest.entity.ContestSubscribed;
import com.game.staticcontest.Static.Contest.repository.ContestRepository;
import com.game.staticcontest.Static.Contest.repository.ContestSubscribedRepository;
import com.game.staticcontest.Static.Contest.service.ContestService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class ContestServiceImplementation implements ContestService {

    @Autowired
    private ContestRepository contestRepository;



    @Autowired
    private ContestSubscribedRepository contestSubscribedRepository;

    @Autowired
    private ContestProducer contestProducer;


    @Override
    public ResponseDTO<ContestDTO> addContest(Contest contest,String userId) {
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
    @Transactional(readOnly = false)
    public ResponseDTO<ContestDTO> getContest(String contestId,String userId) {
        System.out.println("getContest start");
        ResponseDTO<ContestDTO> responseDTO=new ResponseDTO<>();
        responseDTO.setStatus("success");
        responseDTO.setErrorMessage("");
        Contest contest=contestRepository.findOne(contestId);
        ContestDTO contestDTO=new ContestDTO();
        BeanUtils.copyProperties(contest,contestDTO);
        ContestSubscribed contestSubscribed=contestSubscribedRepository.getSubscribedContest(contestId,userId);

        System.out.println("getContest");
        if(contestSubscribed==null) {
            contestDTO.setSubscribed(false);
            contestDTO.setFinished(false);
        }
        else {
            contestDTO.setSubscribed(true);
            contestDTO.setFinished(contestSubscribed.isFinished());
            }

        responseDTO.setResponse(contestDTO);
        return responseDTO;
    }





}
