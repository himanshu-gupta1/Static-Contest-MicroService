package com.game.staticcontest.Static.Contest.controller;

import com.game.staticcontest.Static.Contest.dto.QuestionDetailDTO;
import com.game.staticcontest.Static.Contest.dto.RequestDTO;
import com.game.staticcontest.Static.Contest.dto.ResponseDTO;
import com.game.staticcontest.Static.Contest.entity.Contest;
import com.game.staticcontest.Static.Contest.entity.ContestQuestion;
import com.game.staticcontest.Static.Contest.repository.ContestRepository;
import com.game.staticcontest.Static.Contest.service.ContestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contests/{contestId}/questions")
@CrossOrigin
public class ContestQuestionController {

    @Autowired
    private ContestQuestionService contestQuestionService;

    @Autowired
    private ContestRepository contestRepository;

    @PostMapping("/")
    public ResponseDTO<Void> addQuestions(@PathVariable("contestId") String contestId,@RequestBody RequestDTO<List<QuestionDetailDTO>> requestDTO) {
        ResponseDTO<Void> responseDTO;
        System.out.println("Questions add API hit");
        System.out.println(requestDTO.getRequest());
        try {
            if (verifyAdmin(requestDTO.getUserId())) {

                responseDTO=contestQuestionService.addQuestionToContest(contestId,requestDTO.getRequest());


            } else {
                responseDTO=new ResponseDTO<>();
                responseDTO.setStatus("failure");
                responseDTO.setErrorMessage("Auth Failed");
                responseDTO.setResponse(null);
            }
        }

        catch(Exception e){
            responseDTO=new ResponseDTO<>();
            responseDTO.setStatus("failure");
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setResponse(null);

        }

        return responseDTO;
    }






    //getbyquestionNo.

    public boolean verifyAdmin(String userId) {
        return true;
    }

}
