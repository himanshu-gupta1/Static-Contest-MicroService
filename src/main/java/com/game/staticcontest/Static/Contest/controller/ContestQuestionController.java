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
        ResponseDTO<Void> responseDTO = new ResponseDTO<>();
        try {
            if (verifyAdmin(requestDTO.getUserId())) {

                if (checkQuestions(requestDTO.getRequest())) {
                    int sequence = 1;
                    for (QuestionDetailDTO questionDetailDTO : requestDTO.getRequest()) {

                          Contest contest=new Contest();
                          ContestQuestion contestQuestion=new ContestQuestion();
                          contest.setContestId(contestId);
                          contestQuestion.setContest(contest);
                          contestQuestion.setQuestionId(questionDetailDTO.getQuestionId());
                          contestQuestion.setQuestionSequence((sequence++));
                          contestQuestionService.addQuestion(contestQuestion);

                          Contest contestGet=contestRepository.findOne(contestId);
                          System.out.println(contestGet);
                          contestGet.setActive(true);
                          contestRepository.save(contestGet);
                    }
                    responseDTO.setStatus("success");
                    responseDTO.setErrorMessage("");
                    responseDTO.setResponse(null);

                } else {

                    responseDTO.setStatus("failure");
                    responseDTO.setErrorMessage("Question Ratio Does Not Match");
                    responseDTO.setResponse(null);
                    return responseDTO;

                }
            } else {
                responseDTO.setStatus("failure");
                responseDTO.setErrorMessage("Auth Failed");
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



    public boolean checkQuestions(List<QuestionDetailDTO> questionDetailDTOList)
    {

        //40 -- text based
        //20 -- image based
        //20 -- video based
        //20 -- audio based

        int len=questionDetailDTOList.size();
        int textBasedLength=0;
        int imageBasedLength=0;
        int audioBasedLength=0;
        int videoBasedLength=0;

        for(QuestionDetailDTO questionDetailDTO:questionDetailDTOList)
        {
            if(questionDetailDTO.getQuestionType().trim().toLowerCase().equals("text"))
            {
                textBasedLength++;
            }
            if(questionDetailDTO.getQuestionType().trim().toLowerCase().equals("image"))
            {
                imageBasedLength++;
            }
            if(questionDetailDTO.getQuestionType().trim().toLowerCase().equals("audio"))
            {
                audioBasedLength++;
            }
            if(questionDetailDTO.getQuestionType().trim().toLowerCase().equals("video"))
            {
                videoBasedLength++;
            }
        }

        if(textBasedLength/(double)len==0.4 && imageBasedLength/(double)len==0.2 &&
                videoBasedLength/(double)len==0.2 && audioBasedLength/(double)len==0.2)
        {
           return true;
        }
        else
        {
            return false;
        }


    }


    //getbyquestionNo.

    public boolean verifyAdmin(String userId) {
        return true;
    }

}
