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
import com.game.staticcontest.Static.Contest.service.ContestSubscribedService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    @Autowired
    private ContestSubscribedService contestSubscribedService;








    @PostMapping("/nextQuestion")
    public ResponseDTO<QuestionDetailDTO> getNextQuestion(@PathVariable String contestId,@RequestBody RequestDTO<Void> requestDTO) {

        try {
            if (verifyUser(requestDTO.getUserId())) {

                List<ContestPlayArea> contestPlayArea=contestPlayAreaService.getContestPlayArea(contestId,requestDTO.getUserId());
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
//                    responseDTO.setResponse(null);    //call another microservice to get the question details.// .
                    responseDTO.setResponse(getQuestionFromHussain(contestQuestion.getQuestionId()));

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

    private QuestionDetailDTO getQuestionFromHussain(String questionId) {

//        return null;
        QuestionDetailDTO questionDetailDTO = new QuestionDetailDTO();
        questionDetailDTO.setQuestionId(questionId);
        questionDetailDTO.setQuestionName("LOL this is question " + questionId);
        questionDetailDTO.setQuestionContent("This is content : " + questionId );
        questionDetailDTO.setQuestionType("TEXT");

        OptionDTO optionDTO = new OptionDTO();
        optionDTO.setOptionId("O1");
        optionDTO.setOptionContent("Laugh out Loud");
        List<OptionDTO> optionDTOList = new ArrayList<>();
        optionDTOList.add(optionDTO);
        optionDTOList.add(optionDTO);
        optionDTOList.add(optionDTO);
        optionDTOList.add(optionDTO);

        questionDetailDTO.setOptionDTOList(optionDTOList);
        questionDetailDTO.setDuration(60);

        return questionDetailDTO;
    }


    @PutMapping("/{questionId}/stop")
    public ResponseDTO<Void> submitQuestion(@PathVariable("contestId") String contestId,@PathVariable("questionId") String questionId,@RequestBody RequestDTO<ContestPlayRequestDTO> requestDTO) {

        try {
            ContestPlayArea contestPlayArea=contestPlayAreaService.getContestPlayArea(contestId,questionId,requestDTO.getUserId());
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
                    int duration = 0; //fetch from questionDetai l by passing question id
                    //question detail will give duration + difficulty of that particular question

                    contestPlayArea.setAttempted(true);


                    System.out.println("hello");

                    ResponseDTO<ContestDTO> responseDTO = contestService.getContest(contestId,requestDTO.getUserId());
                    System.out.println("hello");
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
                    System.out.println("hello");
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


    @PutMapping("/{questionId}/skip")
    public ResponseDTO<Void> skipQuestion(@PathVariable("contestId") String contestId, @PathVariable("questionId") String questionId, @RequestBody RequestDTO<Void> requestDTO) {

        ResponseDTO<Void> responseDTO = new ResponseDTO<>();
        try {
            ResponseDTO<ContestDTO> responseDTO1 = contestService.getContest(contestId,requestDTO.getUserId());
            int skipsAllowed = responseDTO1.getResponse().getSkips();
            int noOfSkips = contestPlayAreaService.getNoOfSkips(contestId,requestDTO.getUserId());
            ContestPlayArea contestPlayArea=contestPlayAreaService.getContestPlayArea(contestId,questionId,requestDTO.getUserId());
            if (verifyUser(requestDTO.getUserId())) {
                if(noOfSkips < skipsAllowed){
                    Date date = new Date();
                    contestPlayArea.setSkipped(date.getTime());
                    contestPlayAreaService.addContestPlayArea(contestPlayArea);
                    responseDTO.setStatus("success");
                    responseDTO.setErrorMessage("");
                    responseDTO.setResponse(null);
                }
                else{
                    System.out.println("No more Skips Allowed");
                    responseDTO.setStatus("failure");
                    responseDTO.setErrorMessage("No more skips allowed");
                    responseDTO.setResponse(null);
                }

//                return contestService.addContest(contest);
            } else {
                responseDTO.setStatus("failure");
                responseDTO.setErrorMessage("Auth Failed");
                responseDTO.setResponse(null);
            }
        } catch (Exception e) {
            responseDTO.setStatus("failure");
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setResponse(null);
        }

        return responseDTO;
    }


    @GetMapping("/{userId}")
    public List<ContestPlayArea> getContestPlayArea(@PathVariable String contestId,@PathVariable String userId) {

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






    @PostMapping("/skippedQuestion")
    public ResponseDTO<QuestionDetailDTO> getNextSkippedQuestion(@PathVariable("contestId") String contestId, @RequestBody RequestDTO<Void> requestDTO) {

        try {
            if (verifyUser(requestDTO.getUserId())) {

                ContestPlayArea contestPlayArea = contestPlayAreaService.getNextSkippedQuestion(contestId, requestDTO.getUserId());
                System.out.println(contestPlayArea);
                if (contestPlayArea == null) {

                    ResponseDTO<QuestionDetailDTO> responseDTO = new ResponseDTO<>();
                    responseDTO.setStatus("failure");
                    responseDTO.setErrorMessage("No Skipped Questions Availaible");
                    responseDTO.setResponse(null);
                    return responseDTO;
                } else {

                    ResponseDTO<QuestionDetailDTO> responseDTO = new ResponseDTO<>();
                    responseDTO.setStatus("success");
                    responseDTO.setErrorMessage("");
                    String questionId=contestPlayArea.getQuestionId();
                    //get the question detail dto from the another microservice and then return it back....for now
                    //setting it to null .. will change after integration
                    //set the duration of the skipped question to duration-(skipped time-start time)
                    //(skipped time - start time ) is in milliseconds so remember to change the type appropriately
                    responseDTO.setResponse(null);   //
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



    @PostMapping("/submit")
    public ResponseDTO<ContestSubscribedDTO> submitContest(@PathVariable("contestId") String contestId, @RequestBody RequestDTO<Void> requestDTO) {

        try {
            if (verifyUser(requestDTO.getUserId())) {


                return contestSubscribedService.finish(contestId,requestDTO.getUserId());



            } else {
                ResponseDTO<ContestSubscribedDTO> responseDTO = new ResponseDTO<>();
                responseDTO.setStatus("failure");
                responseDTO.setErrorMessage("Auth Failed");
                responseDTO.setResponse(null);
                return responseDTO;
            }
        } catch (Exception e) {
            ResponseDTO<ContestSubscribedDTO> responseDTO = new ResponseDTO<>();
            responseDTO.setStatus("failure");
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setResponse(null);
            return responseDTO;

        }

    }










}
