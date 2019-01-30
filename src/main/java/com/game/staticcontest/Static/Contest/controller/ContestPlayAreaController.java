package com.game.staticcontest.Static.Contest.controller;

import com.game.staticcontest.Static.Contest.dto.*;
import com.game.staticcontest.Static.Contest.entity.Contest;
import com.game.staticcontest.Static.Contest.entity.ContestPlayArea;
import com.game.staticcontest.Static.Contest.entity.ContestQuestion;
import com.game.staticcontest.Static.Contest.repository.ContestQuestionRepository;
import com.game.staticcontest.Static.Contest.repository.ContestRepository;
import com.game.staticcontest.Static.Contest.service.ContestPlayAreaService;
import com.game.staticcontest.Static.Contest.service.ContestQuestionService;
import com.game.staticcontest.Static.Contest.service.ContestService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/contests/{contestId}/play")
public class ContestPlayAreaController {

    @Autowired
    private ContestPlayAreaService contestPlayAreaService;


    @Autowired
    private ContestQuestionService contestQuestionService;

    @Autowired
    private ContestService contestService;




    @PostMapping("/nextQuestion")
    public ResponseDTO<QuestionDetailDTO> getNextQuestion(@PathVariable String contestId,@RequestBody RequestDTO<Void> requestDTO) {

        try {
            if (verifyUser(requestDTO.getUserId())) {

                ContestPlayArea contestPlayArea=contestPlayAreaService.getContestPlayArea(contestId,requestDTO.getUserId());
                if(contestPlayArea!=null)
                {
                    String questionSeq=contestPlayAreaService.getMaximumQuestionSequence(contestId,requestDTO.getUserId());
                    int qs=Integer.parseInt(questionSeq)+1;
                    ContestQuestion contestQuestion=contestQuestionService.findByQuestionSequence((qs+1)+"");
                    ContestPlayArea contestPlayArea1=new ContestPlayArea();
                    Contest contest=new Contest();
                    contest.setContestId(contestId);
                    contestPlayArea1.setContest(contest);
                    contestPlayArea1.setQuestionId(contestQuestion.getQuestionId());
                    contestPlayArea1.setAttempted(false);
                    contestPlayArea1.setUserId(requestDTO.getUserId());
                    contestPlayArea1.setSkipped(-1);
                    contestPlayArea1.setScore(0);
                    contestPlayArea1.setQuestionSequence(contestQuestion.getQuestionSequence());
                    contestPlayAreaService.addContestPlayArea(contestPlayArea1);
                   // contestPlayArea1.set

                    //setting date
                    Date date=new Date();
                    contestPlayArea1.setStartTime(date.getTime());




                    ResponseDTO<QuestionDetailDTO> responseDTO=new ResponseDTO<>();
                    responseDTO.setStatus("success");
                    responseDTO.setErrorMessage("");
                    responseDTO.setResponse(null);    //call another microservice to get the question details.// .

                    return responseDTO;

                }
                else
                {
                    ContestQuestion contestQuestion=contestQuestionService.findByQuestionSequence("1");
                    String questionId=contestQuestion.getQuestionId();//call another microservice to get the question details.//// .


                    ContestPlayArea contestPlayArea1=new ContestPlayArea();
                    Contest contest=new Contest();
                    contest.setContestId(contestId);
                    contestPlayArea1.setContest(contest);
                    contestPlayArea1.setQuestionId(contestQuestion.getQuestionId());
                    contestPlayArea1.setAttempted(false);
                    contestPlayArea1.setUserId(requestDTO.getUserId());
                    contestPlayArea1.setSkipped(-1);
                    contestPlayArea1.setScore(0);
                    contestPlayArea1.setQuestionSequence(contestQuestion.getQuestionSequence());
                    // contestPlayArea1.set

                    //setting start time
                    Date date=new Date();
                    contestPlayArea1.setStartTime(date.getTime());

                    ResponseDTO<QuestionDetailDTO> responseDTO=new ResponseDTO<>();
                    responseDTO.setStatus("success");
                    responseDTO.setErrorMessage("");
                    responseDTO.setResponse(null);    //call another microservice to get the question details.// .

                    contestPlayAreaService.addContestPlayArea(contestPlayArea1);
                    return responseDTO;

                }
            } else {
                ResponseDTO<QuestionDetailDTO> responseDTO = new ResponseDTO<>();
                responseDTO.setStatus("failure");
                responseDTO.setErrorMessage("Auth Failed");
                responseDTO.setResponse(null);
                return responseDTO;
            }
        } catch (Exception e) {
            ResponseDTO<QuestionDetailDTO> responseDTO = new ResponseDTO<>();
            responseDTO.setStatus("failure");
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setResponse(null);
            return responseDTO;

        }

    }



    @PutMapping("/{questionId}/stop")
    public ResponseDTO<Void> submitQuestion(@PathVariable("contestId") String contestId,@PathVariable("questionId") String questionId,@RequestBody RequestDTO<ContestPlayRequestDTO> requestDTO) {

        try {
            ContestPlayArea contestPlayArea=contestPlayAreaService.getContestPlayArea(contestId,requestDTO.getUserId());
            if (verifyUser(requestDTO.getUserId())) {
                if(requestDTO.getRequest().getOptionIds().equals("")){

                    contestPlayArea.setScore(0);
                    //no click or click on submit without clicking any radio button
                    contestPlayAreaService.addContestPlayArea(contestPlayArea);

                }
                else{
                    System.out.println("user answer is present");
                    Date date = new Date();
                    double difficultyScore = 0.0;
                    int duration = 0; //fetch from questionDetail by passing question id
                    //question detail will give duration + difficulty of that particular question

                    contestPlayArea.setAttempted(true);



                    ResponseDTO<ContestDTO> responseDTO = contestService.getContest(contestId,requestDTO.getUserId());
                    String difficulty = responseDTO.getResponse().getDifficulty();

                    if(difficulty.trim().toLowerCase().equals("easy")) {
                        difficultyScore = 2.0;

                    }
                    else if(difficulty.trim().toLowerCase().equals("medium")) {
                        difficultyScore = 3.0;
                    }
                    else if(difficulty.trim().toLowerCase().equals("hard")) {
                        difficultyScore = 5.0;
                    }

                    contestPlayArea.setEndTime(date.getTime());
                    //check answer API call needed
                    // if(answer is correct)
                    //set score as 0
                    //else
                    //set score as
                    contestPlayArea.setScore(difficultyScore + timeTaken(contestPlayArea,duration));
                    contestPlayAreaService.addContestPlayArea(contestPlayArea);
                }

//                return contestService.addContest(contest);
            } else {
                ResponseDTO<Void> responseDTO = new ResponseDTO<>();
                responseDTO.setStatus("failure");
                responseDTO.setErrorMessage("Auth Failed");
                responseDTO.setResponse(null);
                return responseDTO;
            }
        } catch (Exception e) {
            ResponseDTO<Void> responseDTO = new ResponseDTO<>();
            responseDTO.setStatus("failure");
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setResponse(null);
            return responseDTO;

        }


        ResponseDTO<Void> responseDTO = new ResponseDTO<>();
        responseDTO.setStatus("success");
        responseDTO.setErrorMessage("");
        responseDTO.setResponse(null);
        return responseDTO;

    }


    @GetMapping("/{userId}")
    public ContestPlayArea getContestPlayArea(@PathVariable String contestId,@PathVariable String userId) {

        return contestPlayAreaService.getContestPlayArea(contestId,userId);


    }



    public boolean verifyUser(String userId) {
        return true;
    }


    public double timeTaken(ContestPlayArea contestPlayArea,int duration) {
        Long startTime = contestPlayArea.getStartTime();
        Long endTime = contestPlayArea.getEndTime();
        long timeTaken=duration*1000-(endTime-startTime);
        String timeTakenInString=timeTaken+"";
        int len=timeTakenInString.length();
        return  timeTaken/(double)Math.pow(10,len);

    }



}
