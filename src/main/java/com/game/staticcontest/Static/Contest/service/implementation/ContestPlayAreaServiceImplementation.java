package com.game.staticcontest.Static.Contest.service.implementation;

import com.contest.notificationProducer.dto.Header;
import com.contest.notificationProducer.dto.SubscriptionNotice;
import com.contest.notificationProducer.notificationEnum.NotificationMedium;
import com.contest.notificationProducer.notificationEnum.NotificationType;
import com.contest.notificationProducer.producer.SubscriptionNoticeProducer;
import com.game.staticcontest.Static.Contest.dto.ContestDTO;
import com.game.staticcontest.Static.Contest.dto.QuestionDetailDTO;
import com.game.staticcontest.Static.Contest.dto.ResponseDTO;
import com.game.staticcontest.Static.Contest.entity.Contest;
import com.game.staticcontest.Static.Contest.entity.ContestPlayArea;
import com.game.staticcontest.Static.Contest.entity.ContestQuestion;
import com.game.staticcontest.Static.Contest.entity.ContestSubscribed;
import com.game.staticcontest.Static.Contest.repository.ContestPlayAreaRepository;
import com.game.staticcontest.Static.Contest.repository.ContestRepository;
import com.game.staticcontest.Static.Contest.repository.ContestSubscribedRepository;
import com.game.staticcontest.Static.Contest.service.ContestPlayAreaService;
import com.game.staticcontest.Static.Contest.service.ContestQuestionService;
import com.game.staticcontest.Static.Contest.service.ContestService;
import com.recommendation.kafka_sdk.contest.PlayQuestionKafkaProducer;
import com.recommendation.kafka_sdk.dto.PlayQuestionKafkaMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class ContestPlayAreaServiceImplementation implements ContestPlayAreaService {

    @Autowired
    private ContestPlayAreaRepository contestPlayAreaRepository;

    @Autowired
    private ContestQuestionService contestQuestionService;


    @Autowired
    private ContestService contestService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PlayQuestionKafkaProducer playQuestionKafkaProducer;

    @Autowired
    private ContestSubscribedRepository contestSubscribedRepository;


    @Autowired
    private Environment environment;




    @Override
    public ResponseDTO<QuestionDetailDTO> fetchNextQuestion(String contestId, String userId) {

        ResponseDTO<QuestionDetailDTO> responseDTO;

        //here logic will be handled that the user cant enter the particular contest without
        //subscribing to that particular contest;

        ContestSubscribed contestSubscribed=contestSubscribedRepository.getSubscribedContest(contestId,userId);
        if(contestSubscribed==null)
        {
            responseDTO=new ResponseDTO<>();
            responseDTO.setStatus("failure");
            responseDTO.setErrorMessage("you can't play the contest without subscribing to it");
            responseDTO.setResponse(null);
        }
        else {


            //....

            List<ContestPlayArea> contestPlayArea = getContestPlayArea(contestId, userId);
            if (contestPlayArea.size() != 0) {
                System.out.println("inside not null");
                Integer questionSeq = getMaximumQuestionSequence(contestId, userId);
                System.out.println(questionSeq);
                int qs = questionSeq + 1;
                ContestQuestion contestQuestion = contestQuestionService.findByQuestionSequence(qs, contestId);
                System.out.println(contestQuestion);
                int numberOfQuestions = contestService.getContest(contestId, userId).getResponse().getNoOfQuestions();
                System.out.println(numberOfQuestions);
                System.out.println(qs + "");
                if ((qs - 1) == numberOfQuestions) {
                    System.out.println("inside null of max seq");
                    responseDTO = new ResponseDTO<>();
                    responseDTO.setStatus("failure");
                    responseDTO.setErrorMessage("check skipped questions");
                    responseDTO.setResponse(null);
                } else {
                    System.out.println("inside not null of max seq");
                    ContestPlayArea contestPlayArea1 = new ContestPlayArea();
                    Contest contest = new Contest();
                    contest.setContestId(contestId);
                    contestPlayArea1.setContest(contest);
                    contestPlayArea1.setQuestionId(contestQuestion.getQuestionId());
                    contestPlayArea1.setAttempted(false);
                    contestPlayArea1.setUserId(userId);
                    contestPlayArea1.setSkipped(-1);
                    contestPlayArea1.setScore(0);
                    contestPlayArea1.setQuestionSequence(contestQuestion.getQuestionSequence());

                    // contestPlayArea1.set

                    //setting date
                    Date date = new Date();
                    contestPlayArea1.setStartTime(date.getTime());

                    addContestPlayArea(contestPlayArea1);


                    responseDTO = new ResponseDTO<>();
                    responseDTO.setStatus("success");
                    responseDTO.setErrorMessage("");
                    //responseDTO.setResponse(null);    call another microservice to get the question details.// .
                    //responseDTO.setResponse(getQuestionFromHussain(contestQuestion.getQuestionId()));
                    responseDTO.setResponse(getQuestionFromServer(contestQuestion.getQuestionId()));
                }

            } else {
                //user is getting the first question


                System.out.println("inside null");
                ContestQuestion contestQuestion = contestQuestionService.findByQuestionSequence(1, contestId);
                String questionId = contestQuestion.getQuestionId();//call another microservice to get the question details.//// .


                ContestPlayArea contestPlayArea1 = new ContestPlayArea();
                Contest contest = new Contest();
                contest.setContestId(contestId);
                contestPlayArea1.setContest(contest);
                contestPlayArea1.setQuestionId(contestQuestion.getQuestionId());
                contestPlayArea1.setAttempted(false);
                contestPlayArea1.setUserId(userId);
                contestPlayArea1.setSkipped(-1);
                contestPlayArea1.setScore(0);
                contestPlayArea1.setQuestionSequence(contestQuestion.getQuestionSequence());
                // contestPlayArea1.set

                //setting start time
                Date date = new Date();
                contestPlayArea1.setStartTime(date.getTime());

                responseDTO = new ResponseDTO<>();
                responseDTO.setStatus("success");
                responseDTO.setErrorMessage("");
                //responseDTO.setResponse(null);    //call another microservice to get the question details.// .
                //responseDTO.setResponse(getQuestionFromHussain(contestQuestion.getQuestionId()));
                responseDTO.setResponse(getQuestionFromServer(contestQuestion.getQuestionId()));
                addContestPlayArea(contestPlayArea1);
            }
        }

        return responseDTO;
    }

    @Override
    public ResponseDTO<Void> submitQuestion(String contestId, String questionId, String userId, String optionIds) {

        ResponseDTO<Void> responseDTO = new ResponseDTO<>();
        ContestPlayArea contestPlayArea = getContestPlayArea(contestId, questionId, userId);
        if (optionIds.equals("")) {
            Date date = new Date();
            contestPlayArea.setEndTime(date.getTime());
            contestPlayArea.setScore(0.0);
            //no click or click on submit without clicking any radio button
            contestPlayArea.setUserAnswer("");
            contestPlayArea.setSkipped(-1);
            contestPlayArea.setAttempted(false);
            addContestPlayArea(contestPlayArea);

        } else {
            System.out.println("user answer is present");
            Date date = new Date();
            double difficultyScore = 0.0;
            int duration = 0; //fetch from questionDetail by passing question id
            //question detail will give duration + difficulty of that particular question
            QuestionDetailDTO questionDetailDTO = getQuestionFromServer(questionId);
            contestPlayArea.setEndTime(date.getTime());
            contestPlayArea.setAttempted(true);
            contestPlayArea.setSkipped(-1);
            contestPlayArea.setUserAnswer(optionIds);

            //update the user score
            if (checkAnswer(questionId, optionIds)) {
                System.out.println("hello");
                String difficulty = questionDetailDTO.getQuestionDifficulty();

                if (difficulty.trim().toLowerCase().equals("easy")) {
                    difficultyScore = 2.0;

                } else if (difficulty.trim().toLowerCase().equals("medium")) {
                    difficultyScore = 3.0;
                } else if (difficulty.trim().toLowerCase().equals("hard")) {
                    difficultyScore = 5.0;
                }

                contestPlayArea.setScore(difficultyScore + timeTaken(contestPlayArea, questionDetailDTO.getDuration() * 1000));
            } else {
                contestPlayArea.setScore(0.0);
            }


            //recomendation system
            sendRecommentdation(userId,questionDetailDTO);
            //........


            System.out.println("hello");


            //check answer API call needed
            // if(answer is incorrect)
            //set score as 0
            //else
            //set score as
            // contestPlayArea.setScore(difficultyScore + timeTaken(contestPlayArea, duration));
            System.out.println("hello");
            addContestPlayArea(contestPlayArea);
        }

        responseDTO.setStatus("success");
        responseDTO.setErrorMessage("");
        responseDTO.setResponse(null);
        return responseDTO;

    }

    @Override
    public ResponseDTO<Void> skipQuestion(String contestId, String questionId, String userId) {

        ResponseDTO<Void> responseDTO = new ResponseDTO<>();
        ResponseDTO<ContestDTO> responseDTO1 = contestService.getContest(contestId, userId);
        int skipsAllowed = responseDTO1.getResponse().getSkips();
        int noOfSkips = getNoOfSkips(contestId, userId);
        ContestPlayArea contestPlayArea = getContestPlayArea(contestId, questionId, userId);
        if (noOfSkips < skipsAllowed) {
            Date date = new Date();
            contestPlayArea.setSkipped(date.getTime());
            addContestPlayArea(contestPlayArea);
            responseDTO.setStatus("success");
            responseDTO.setErrorMessage("");
            responseDTO.setResponse(null);
        } else {
            System.out.println("No more Skips Allowed");
            responseDTO.setStatus("failure");
            responseDTO.setErrorMessage("No more skips allowed");
            responseDTO.setResponse(null);
        }
        return responseDTO;
    }


    @Override
    public List<ContestPlayArea> getContestPlayArea(String contestId, String userId) {
        return contestPlayAreaRepository.getContestPlayArea(contestId, userId);

    }

    @Override
    public ContestPlayArea getContestPlayArea(String contestId, String questionId, String userId) {
        return contestPlayAreaRepository.getContestPlayArea(contestId, questionId, userId);
    }

    @Override
    public Integer getMaximumQuestionSequence(String contestId, String userId) {
        return contestPlayAreaRepository.getMaximumQuestionSequence(contestId, userId);

    }


    @Modifying
    @Override
    @Transactional(readOnly = false)
    public ContestPlayArea addContestPlayArea(ContestPlayArea contestPlayArea) {
        System.out.println("hello");
        return contestPlayAreaRepository.save(contestPlayArea);
    }

    @Override
    public int getNoOfSkips(String contestId, String userId) {
        return contestPlayAreaRepository.getNoOfSkips(contestId, userId);
    }

    @Override
    public ResponseDTO<QuestionDetailDTO> getNextSkippedQuestion(String contestId, String userId) {
        ResponseDTO<QuestionDetailDTO> responseDTO;
        ContestPlayArea contestPlayArea = contestPlayAreaRepository.getNextSkippedQuestion(contestId, userId);
        System.out.println(contestPlayArea);
        if (contestPlayArea == null) {

            responseDTO = new ResponseDTO<>();
            responseDTO.setStatus("failure");
            responseDTO.setErrorMessage("No Skipped Questions Availaible");
            responseDTO.setResponse(null);
            System.out.println(responseDTO);
        } else {

            responseDTO = new ResponseDTO<>();
            responseDTO.setStatus("success");
            responseDTO.setErrorMessage("");
            String questionId = contestPlayArea.getQuestionId();
            //get the question detail dto from the another microservice and then return it back....for now
            //setting it to null .. will change after integration
            //set the duration of the skipped question to duration-(skipped time-start time)
            //(skipped time - start time ) is in milliseconds so remember to change the type appropriately
            //responseDTO.setResponse(null);   //
            // responseDTO.setResponse(getQuestionFromHussain(questionId));
            responseDTO.setResponse(getQuestionFromServer(questionId));
        }

        return responseDTO;
    }


    //helper methods

    public double timeTaken(ContestPlayArea contestPlayArea, int duration) {
        Long startTime = contestPlayArea.getStartTime();
        Long endTime = contestPlayArea.getEndTime();
        double timeTaken;
        if (endTime - startTime > duration) {
            timeTaken = 0;
        } else {
            timeTaken = duration - (endTime - startTime);
            System.out.println("/n/n/n/n/n" + timeTaken + "/n/n/n/n/n");
            String timeTakenInString[] = (timeTaken + "").split("\\.");
            int len = timeTakenInString[0].length();
            timeTaken = timeTaken / (double) Math.pow(10, len);
            System.out.println("/n/n/n/n/n" + timeTaken + "/n/n/n/n/n/n");
        }

        return timeTaken;

    }

    public QuestionDetailDTO getQuestionFromServer(String questionId) {
        String URL=environment.getProperty("screening.output.server.address")+"/screeningoutput/getByQuestionId/" + questionId;
       // String URL = "http://10.177.7.115:8000/screeningoutput/getByQuestionId/" + questionId;
        ResponseEntity<QuestionDetailDTO> response = restTemplate.getForEntity(URL, QuestionDetailDTO.class);
        System.out.println(response.getBody());
        return response.getBody();

    }

    public boolean checkAnswer(String questionId, String optionIds) {
        String URL=environment.getProperty("screening.output.server.address")+"/screeningoutput/checkAnswer";
       // String URL = "http://10.177.7.115:8000/screeningoutput/checkAnswer";
        HashMap<String, String> hash = new HashMap<>();
        hash.put("questionId", questionId);
        hash.put("userAnswer", optionIds);
        HttpEntity<HashMap<String, String>> request = new HttpEntity<>(hash, null);
        ResponseEntity<Boolean> response = restTemplate.postForEntity(URL, request, Boolean.class);
        System.out.println(response.getBody());
        return response.getBody();

    }



    public void sendRecommentdation(String userId,QuestionDetailDTO questionDetailDTO)
    {

        PlayQuestionKafkaMessage playQuestionKafkaMessage = new PlayQuestionKafkaMessage();
        playQuestionKafkaMessage.setUserId(userId);
        playQuestionKafkaMessage.setCategory(questionDetailDTO.getQuestionCategory());
        playQuestionKafkaMessage.setTimestamp(System.nanoTime());
        playQuestionKafkaProducer.sendPlayQuestionKafkaMessage(playQuestionKafkaMessage);

    }





}
