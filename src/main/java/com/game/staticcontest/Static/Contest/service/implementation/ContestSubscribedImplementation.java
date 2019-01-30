package com.game.staticcontest.Static.Contest.service.implementation;


import com.game.staticcontest.Static.Contest.dto.ContestSubscribedDTO;
import com.game.staticcontest.Static.Contest.dto.ResponseDTO;
import com.game.staticcontest.Static.Contest.entity.Contest;
import com.game.staticcontest.Static.Contest.entity.ContestSubscribed;
import com.game.staticcontest.Static.Contest.repository.ContestRepository;
import com.game.staticcontest.Static.Contest.repository.ContestSubscribedRepository;
import com.game.staticcontest.Static.Contest.service.ContestSubscribedService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ContestSubscribedImplementation implements ContestSubscribedService {


    @Autowired
    private ContestSubscribedRepository contestSubscribedRepository;

    @Autowired
    private Environment env;

    @Override
    public ResponseDTO<ContestSubscribedDTO> subscribe(String contestId, String userId) {

        int maxLimit=Integer.parseInt(env.getProperty("x"));
        System.out.println(maxLimit+"");
        List<ContestSubscribed> contestSubscribedList=contestSubscribedRepository.getAllContestByUserId(userId);
        System.out.println("hello after");
        if(contestSubscribedList.size()<=maxLimit) {
            ContestSubscribed contestSubscribed = new ContestSubscribed();
            Contest contest = new Contest();
            contest.setContestId(contestId);
            contestSubscribed.setContest(contest);
            contestSubscribed.setUserId(userId);
            contestSubscribed.setFinished(false);
            contestSubscribed.setScore(0.0);
            System.out.println("hello");
            ContestSubscribed contestSubscribedAdded=contestSubscribedRepository.save(contestSubscribed);
            ResponseDTO<ContestSubscribedDTO> responseDTO = new ResponseDTO<>();
            responseDTO.setStatus("success");
            responseDTO.setErrorMessage("");
            ContestSubscribedDTO contestSubscribedDTO=new ContestSubscribedDTO();
            BeanUtils.copyProperties(contestSubscribedAdded,contestSubscribedDTO);
            responseDTO.setResponse(contestSubscribedDTO);
            return responseDTO;
        }
        else
        {
            ResponseDTO<ContestSubscribedDTO> responseDTO=new ResponseDTO<>();
            responseDTO.setStatus("failure");
            responseDTO.setErrorMessage("You have reached your maximum subscription limit");
            responseDTO.setResponse(null);
            return responseDTO;
        }
    }

    @Override
    public ResponseDTO<Void> finish(String contestId, String userId) {

        //set finished to true and update score of contest by fetching all the questions of that particular contest by using
        //user id




        ContestSubscribed contestSubscribed=contestSubscribedRepository.getSubscribedContest(contestId,userId);
        //set the score here
        contestSubscribed.setFinished(true);
        contestSubscribedRepository.save(contestSubscribed);

        ResponseDTO<Void> responseDTO=new ResponseDTO<>();
        responseDTO.setStatus("success");
        responseDTO.setErrorMessage("");
        responseDTO.setResponse(null);

        return responseDTO;
    }
}
