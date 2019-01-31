package com.game.staticcontest.Static.Contest.service.implementation;


import com.game.staticcontest.Static.Contest.dto.ContestSubscribedDTO;
import com.game.staticcontest.Static.Contest.dto.ResponseDTO;
import com.game.staticcontest.Static.Contest.dto.SubmitSubscriptionDTO;
import com.game.staticcontest.Static.Contest.entity.Contest;
import com.game.staticcontest.Static.Contest.entity.ContestPlayArea;
import com.game.staticcontest.Static.Contest.entity.ContestSubscribed;
import com.game.staticcontest.Static.Contest.repository.ContestPlayAreaRepository;
import com.game.staticcontest.Static.Contest.repository.ContestRepository;
import com.game.staticcontest.Static.Contest.repository.ContestSubscribedRepository;
import com.game.staticcontest.Static.Contest.service.ContestSubscribedService;
import com.recommendation.kafka_sdk.contest.SubscribeKafkaProducer;
import com.recommendation.kafka_sdk.dto.SubscribeContestKafkaMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = false)
public class ContestSubscribedImplementation implements ContestSubscribedService {


    @Autowired
    private ContestSubscribedRepository contestSubscribedRepository;

    @Autowired
    private Environment env;


    @Autowired
    private ContestPlayAreaRepository contestPlayAreaRepository;



    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private SubscribeKafkaProducer subscribeKafkaProducer;




    @Override
    public ResponseDTO<ContestSubscribedDTO> subscribe(String contestId, String userId) {

        int maxLimit=Integer.parseInt(env.getProperty("x"));
        System.out.println(maxLimit+"");
        List<ContestSubscribed> contestSubscribedList=contestSubscribedRepository.getAllContestByUserId(userId);
        System.out.println("hello after");
        if(contestSubscribedList.size()<maxLimit) {
            ContestSubscribed contestSubscribed = new ContestSubscribed();
            Contest contest = new Contest();
            contest.setContestId(contestId);
            contestSubscribed.setContest(contest);
            contestSubscribed.setUserId(userId);
            contestSubscribed.setFinished(false);
            contestSubscribed.setScore(0.0);
            System.out.println("hello");

            //saving in the contest_subscribed table
            ContestSubscribed contestSubscribedAdded=contestSubscribedRepository.save(contestSubscribed);


            //send the subscription info to the kafka recommendations system
            SubscribeContestKafkaMessage subscribeContestKafkaMessage=new SubscribeContestKafkaMessage();
            subscribeContestKafkaMessage.setUserId(userId);

            Contest contestGet=contestRepository.findOne(contestId);
            subscribeContestKafkaMessage.setCategory(contestGet.getCategoryId());
            subscribeContestKafkaMessage.setTimestamp(System.nanoTime());

            subscribeKafkaProducer.sendSubscribeKafkaMessage(subscribeContestKafkaMessage);

            //......

            //sending the update for the same to another microservice...
            SubmitSubscriptionDTO submitSubscriptionDTO=new SubmitSubscriptionDTO();
            submitSubscriptionDTO.setContestId(contestId);
            //Contest contestGet=contestRepository.findOne(contestId);
            submitSubscriptionDTO.setContestName(contestGet.getName());
            submitSubscriptionDTO.setUserId(userId);
            //call the api to send the submit subscription dto


            //...........
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
    public ResponseDTO<ContestSubscribedDTO> finish(String contestId, String userId) {

        //set finished to true and update score of contest by fetching all the questions of that particular contest by using
        //user id

        List<ContestPlayArea> contestPlayAreaList=contestPlayAreaRepository.getContestPlayArea(contestId,userId);
        double score=0.0;
        for(ContestPlayArea contestPlayArea:contestPlayAreaList)
        {
            score=score+contestPlayArea.getScore();
        }




        ContestSubscribed contestSubscribed=contestSubscribedRepository.getSubscribedContest(contestId,userId);
        //set the score here
        contestSubscribed.setScore(score);
        contestSubscribed.setFinished(true);

        contestSubscribedRepository.save(contestSubscribed);

        ResponseDTO<ContestSubscribedDTO> responseDTO=new ResponseDTO<>();
        responseDTO.setStatus("success");
        responseDTO.setErrorMessage("");
        ContestSubscribedDTO contestSubscribedDTO=new ContestSubscribedDTO();
        BeanUtils.copyProperties(contestSubscribed,contestSubscribedDTO);
        responseDTO.setResponse(contestSubscribedDTO);

        return responseDTO;
    }
}
