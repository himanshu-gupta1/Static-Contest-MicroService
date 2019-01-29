package com.game.staticcontest.Static.Contest.controller;

import com.game.staticcontest.Static.Contest.dto.QuestionDTO;
import com.game.staticcontest.Static.Contest.dto.RequestDTO;
import com.game.staticcontest.Static.Contest.dto.ResponseDTO;
import com.game.staticcontest.Static.Contest.entity.ContestQuestion;
import com.game.staticcontest.Static.Contest.service.ContestQuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contests/{contestId}/questions")
@CrossOrigin
public class ContestQuestionController {

    @Autowired
    private ContestQuestionService contestQuestionService;

    @PostMapping("/")
    public ResponseDTO<Void> addQuestions(@RequestBody RequestDTO<List<QuestionDTO>> requestDTO) {
        ResponseDTO<Void> responseDTO = new ResponseDTO<>();
        try{
            if(verifyAdmin(requestDTO.getUserId())) {
                int i=1;
                for(QuestionDTO questionDTO:requestDTO.getRequest()){
                    ContestQuestion contestQuestion = new ContestQuestion();
                    BeanUtils.copyProperties(questionDTO,contestQuestion);
                    contestQuestion.setQuestionSequence(Integer.toString(i++));
                    responseDTO = contestQuestionService.addQuestion(contestQuestion);
                }
            }
            else {
                responseDTO.setStatus("Auth Failed");
                responseDTO.setErrorMessage("");
                responseDTO.setResponse(null);
            }
            return responseDTO;
        }
        catch(Exception e){
            responseDTO.setStatus("failure");
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setResponse(null);
            return responseDTO;
        }
    }

    public boolean verifyAdmin(String userId) {
        return true;
    }

}
