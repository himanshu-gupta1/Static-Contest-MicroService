package com.game.staticcontest.Static.Contest.controller;

import com.game.staticcontest.Static.Contest.dto.*;
import com.game.staticcontest.Static.Contest.entity.ContestPlayArea;
import com.game.staticcontest.Static.Contest.service.ContestPlayAreaService;
import com.game.staticcontest.Static.Contest.service.ContestQuestionService;
import com.game.staticcontest.Static.Contest.service.ContestService;
import com.game.staticcontest.Static.Contest.service.ContestSubscribedService;
import com.recommendation.kafka_sdk.contest.PlayQuestionKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PlayQuestionKafkaProducer playQuestionKafkaProducer;


    @PostMapping("/nextQuestion")
    public ResponseDTO<QuestionDetailDTO> getNextQuestion(@PathVariable String contestId, @RequestBody RequestDTO<Void> requestDTO) {

        ResponseDTO<QuestionDetailDTO> responseDTO;
        try {
            if (verifyUser(requestDTO.getUserId())) {
                responseDTO = contestPlayAreaService.fetchNextQuestion(contestId, requestDTO.getUserId());
            } else {
                responseDTO = new ResponseDTO<>();
                responseDTO.setStatus("failure");
                responseDTO.setErrorMessage("Auth Failed");
                responseDTO.setResponse(null);
            }
        } catch (Exception e) {
            responseDTO = new ResponseDTO<>();
            responseDTO.setStatus("failure");
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setResponse(null);
        }

        return responseDTO;

    }

//    private QuestionDetailDTO getQuestionFromHussain(String questionId) {
//
////        return null;
//        QuestionDetailDTO questionDetailDTO = new QuestionDetailDTO();
//        questionDetailDTO.setQuestionId(questionId);
//        questionDetailDTO.setQuestionName("LOL this is question " + questionId);
//        questionDetailDTO.setQuestionContent("This is content : " + questionId);
//        questionDetailDTO.setQuestionType("TEXT");
//
//        OptionDTO optionDTO = new OptionDTO();
//        optionDTO.setOptionId("O1");
//        optionDTO.setOptionContent("Laugh out Loud");
//        List<OptionDTO> optionDTOList = new ArrayList<>();
//        optionDTOList.add(optionDTO);
//        optionDTOList.add(optionDTO);
//        optionDTOList.add(optionDTO);
//        optionDTOList.add(optionDTO);
//
//        questionDetailDTO.setOptionDTOList(optionDTOList);
//        questionDetailDTO.setDuration(60);
//
//        return questionDetailDTO;
//    }

    @PutMapping("/{questionId}/stop")
    public ResponseDTO<Void> submitQuestion(@PathVariable("contestId") String contestId, @PathVariable("questionId") String questionId, @RequestBody RequestDTO<ContestPlayRequestDTO> requestDTO) {
        ResponseDTO<Void> responseDTO;
        System.out.println("Stop : " + contestId + " : " + questionId + " : " + requestDTO);
        try {
            if (verifyUser(requestDTO.getUserId())) {
                responseDTO = contestPlayAreaService.submitQuestion(contestId,questionId,requestDTO.getUserId(),requestDTO.getRequest().getOptionIds());
            }
            else {
                responseDTO = new ResponseDTO<>();
                responseDTO.setStatus("failure");
                responseDTO.setErrorMessage("Auth Failed");
                responseDTO.setResponse(null);
            }
        } catch (Exception e) {
            responseDTO = new ResponseDTO<>();
            responseDTO.setStatus("failure");
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setResponse(null);
        }

        return responseDTO;

    }


    @PutMapping("/{questionId}/skip")
    public ResponseDTO<Void> skipQuestion(@PathVariable("contestId") String contestId, @PathVariable("questionId") String questionId, @RequestBody RequestDTO<Void> requestDTO) {

        ResponseDTO<Void> responseDTO;
        try {
            if (verifyUser(requestDTO.getUserId())) {
                responseDTO=contestPlayAreaService.skipQuestion(contestId,questionId,requestDTO.getUserId());
            }
            else {
                responseDTO=new ResponseDTO<>();
                responseDTO.setStatus("failure");
                responseDTO.setErrorMessage("Auth Failed");
                responseDTO.setResponse(null);
            }
        } catch (Exception e) {
            responseDTO=new ResponseDTO<>();
            responseDTO.setStatus("failure");
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setResponse(null);
        }

        return responseDTO;
    }


    @GetMapping("/{userId}")
    public List<ContestPlayArea> getContestPlayArea(@PathVariable String contestId, @PathVariable String userId) {

        return contestPlayAreaService.getContestPlayArea(contestId, userId);


    }







    @PostMapping("/skippedQuestion")
    public ResponseDTO<QuestionDetailDTO> getNextSkippedQuestion(@PathVariable("contestId") String contestId, @RequestBody RequestDTO<Void> requestDTO) {
        ResponseDTO<QuestionDetailDTO> responseDTO;
        System.out.println("Skipped Question Request : " + requestDTO + " " + contestId);
        try {
            if (verifyUser(requestDTO.getUserId())) {

                responseDTO = contestPlayAreaService.getNextSkippedQuestion(contestId, requestDTO.getUserId());

            } else {
                responseDTO = new ResponseDTO<>();
                responseDTO.setStatus("failure");
                responseDTO.setErrorMessage("Auth Failed");
                responseDTO.setResponse(null);
                System.out.println(responseDTO);
            }
        } catch (Exception e) {
            responseDTO = new ResponseDTO<>();
            responseDTO.setStatus("failure");
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setResponse(null);
            System.out.println(responseDTO);
        }

        return responseDTO;
    }


    @PostMapping("/submit")
    public ResponseDTO<ContestSubscribedDTO> submitContest(@PathVariable("contestId") String contestId, @RequestBody RequestDTO<Void> requestDTO) {
        ResponseDTO<ContestSubscribedDTO> responseDTO;
        System.out.println("Submit check : " + requestDTO + "Contest Id : " + contestId);
        try {
            if (verifyUser(requestDTO.getUserId())) {


                System.out.println("inside try");
                responseDTO = contestSubscribedService.finish(contestId, requestDTO.getUserId());
                System.out.println("inside try after");


            } else {
                responseDTO = new ResponseDTO<>();
                responseDTO.setStatus("failure");
                responseDTO.setErrorMessage("Auth Failed");
                responseDTO.setResponse(null);
            }
        } catch (Exception e) {
            System.out.println("inside catch");
            responseDTO = new ResponseDTO<>();
            responseDTO.setStatus("failure");
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setResponse(null);
        }

        return responseDTO;


    }

    public boolean verifyUser(String userId) {
        return true;
    }


}
