package com.game.staticcontest.Static.Contest.service.implementation;


import com.contest.notificationProducer.dto.Header;
import com.contest.notificationProducer.dto.SubscriptionNotice;
import com.contest.notificationProducer.exception.FieldsCanNotBeEmpty;
import com.contest.notificationProducer.notificationEnum.NotificationMedium;
import com.contest.notificationProducer.notificationEnum.NotificationType;
import com.contest.notificationProducer.producer.SubscriptionNoticeProducer;
import com.game.staticcontest.Static.Contest.dto.*;
import com.game.staticcontest.Static.Contest.entity.Contest;
import com.game.staticcontest.Static.Contest.entity.ContestPlayArea;
import com.game.staticcontest.Static.Contest.entity.ContestSubscribed;
import com.game.staticcontest.Static.Contest.repository.ContestPlayAreaRepository;
import com.game.staticcontest.Static.Contest.repository.ContestRepository;
import com.game.staticcontest.Static.Contest.repository.ContestSubscribedRepository;
import com.game.staticcontest.Static.Contest.service.ContestService;
import com.game.staticcontest.Static.Contest.service.ContestSubscribedService;
import com.game.staticcontest.Static.Contest.thread.KafkaSubscriptionThread;
import com.game.staticcontest.Static.Contest.thread.SubscriptionNotificationThread;
import com.recommendation.kafka_sdk.contest.PlayQuestionKafkaProducer;
import com.recommendation.kafka_sdk.contest.SubscribeKafkaProducer;
import com.recommendation.kafka_sdk.dto.SubscribeContestKafkaMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
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
    private ContestService contestService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SubscriptionNoticeProducer subscriptionNoticeProducer;


    @Autowired
    private PlayQuestionKafkaProducer playQuestionKafkaProducer;

    @Override
    public ResponseDTO<ContestSubscribedDTO> subscribe(String contestId, String userId) {

        ContestSubscribed contestSubscribed1 = contestSubscribedRepository.getSubscribedContest(contestId,userId);
        ResponseDTO<ContestSubscribedDTO> responseDTO;
        if(contestSubscribed1==null) {
            int maxLimit = Integer.parseInt(env.getProperty("x"));
            System.out.println(maxLimit + "");
            List<ContestSubscribed> contestSubscribedList = contestSubscribedRepository.getAllContestByUserId(userId);
            System.out.println("hello after");
            if (contestSubscribedList.size() < maxLimit) {
                ContestSubscribed contestSubscribed = new ContestSubscribed();
                Contest contest = new Contest();
                contest.setContestId(contestId);
                contestSubscribed.setContest(contest);
                contestSubscribed.setUserId(userId);
                contestSubscribed.setFinished(false);
                contestSubscribed.setScore(0.0);
                System.out.println("hello");


                //saving in the contest_subscribed table
                ContestSubscribed contestSubscribedAdded = contestSubscribedRepository.save(contestSubscribed);


                //sending the notification
                Thread myThread = new SubscriptionNotificationThread(contestId,userId,subscriptionNoticeProducer,contestRepository);
                myThread.start();
                //sendSubscriptionNotification();
                //......


                //send the subscription info to the kafka recommendations system
                Contest contestGet = contestRepository.findOne(contestId);
                Thread kafkaThread = new KafkaSubscriptionThread(userId,contestGet.getCategoryId(),playQuestionKafkaProducer);
                kafkaThread.start();
                //......

                //sending the update for the same to another microservice...
                SubmitSubscriptionDTO submitSubscriptionDTO = new SubmitSubscriptionDTO();
                submitSubscriptionDTO.setContestId(contestId);
                //Contest contestGet=contestRepository.findOne(contestId);
                submitSubscriptionDTO.setContestName(contestGet.getName());
                submitSubscriptionDTO.setUserId(userId);
                String message = submitSubscriptionInformation(submitSubscriptionDTO);
                System.out.println(message);
                //call the api to send the submit subscription dto


                //...........
                responseDTO = new ResponseDTO<>();
                responseDTO.setStatus("success");
                responseDTO.setErrorMessage("");
                ContestSubscribedDTO contestSubscribedDTO = new ContestSubscribedDTO();
                BeanUtils.copyProperties(contestSubscribedAdded, contestSubscribedDTO);
                responseDTO.setResponse(contestSubscribedDTO);
                return responseDTO;
            } else {
                responseDTO = new ResponseDTO<>();
                responseDTO.setStatus("failure");
                responseDTO.setErrorMessage("You have reached your maximum subscription limit");
                responseDTO.setResponse(null);
                return responseDTO;
            }
        }
        else {
            responseDTO = new ResponseDTO<>();
            responseDTO.setStatus("failure");
            responseDTO.setErrorMessage("You are already subscribed to this contest");
            responseDTO.setResponse(null);
        }
        return responseDTO;
    }

    @Override
    public ResponseDTO<ContestSubscribedDTO> finish(String contestId, String userId) {

        //set finished to true and update score of contest by fetching all the questions of that particular contest by using
        //user id


        System.out.println("hello");
        List<ContestPlayArea> contestPlayAreaList = contestPlayAreaRepository.getContestPlayArea(contestId, userId);
        double score = 0.0;
        for (ContestPlayArea contestPlayArea : contestPlayAreaList) {
            score = score + contestPlayArea.getScore();
        }


        ContestSubscribed contestSubscribed = contestSubscribedRepository.getSubscribedContest(contestId, userId);
        System.out.println(contestSubscribed);
        //set the score here
        contestSubscribed.setScore(score);
        contestSubscribed.setFinished(true);

        contestSubscribedRepository.save(contestSubscribed);

        ResponseDTO<ContestSubscribedDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setStatus("success");
        responseDTO.setErrorMessage("");
        ContestSubscribedDTO contestSubscribedDTO = new ContestSubscribedDTO();
        BeanUtils.copyProperties(contestSubscribed, contestSubscribedDTO);
        responseDTO.setResponse(contestSubscribedDTO);

        ResponseDTO<ContestDTO> contest = contestService.getContest(contestId, userId);

        SubmitContestDTO submitContestDTO = new SubmitContestDTO();
        submitContestDTO.setContestId(contestId);
        submitContestDTO.setScore(score);
        submitContestDTO.setUserId(userId);
        submitContestDTO.setContestName(contest.getResponse().getName());

        String res=submitContest(submitContestDTO);
        System.out.println(res);


        return responseDTO;
    }

    public String submitContest(SubmitContestDTO submitContestDTO) {
        String URL=env.getProperty("leaderboard.server.address")+"/getReport/addToLeaderboard";
        //String URL = "http://10.177.7.118:8000/getReport/addToLeaderboard";
        ResponseEntity<String> response = restTemplate.postForEntity(URL, submitContestDTO, String.class);
        return response.getBody();
    }


    public String submitSubscriptionInformation(SubmitSubscriptionDTO submitSubscriptionDTO) {
        String URL=env.getProperty("leaderboard.server.address")+"/getReport/addNewSubscriber";
        //String URL = "http://10.177.7.118:8000/getReport/addNewSubscriber";
        ResponseEntity<String> response = restTemplate.postForEntity(URL, submitSubscriptionDTO, String.class);
        return response.getBody();
    }


    public List<String> getFollowersFromUserId(String userId) {
        String URL = env.getProperty("follower.server.address")+"/follow/getFollow/" + userId;
        ResponseEntity<List<String>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
        });
        return responseEntity.getBody();

    }


}
