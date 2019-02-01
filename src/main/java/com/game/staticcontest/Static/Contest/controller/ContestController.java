package com.game.staticcontest.Static.Contest.controller;

import com.game.staticcontest.Static.Contest.dto.ContestDTO;
import com.game.staticcontest.Static.Contest.dto.RequestDTO;
import com.game.staticcontest.Static.Contest.dto.ResponseDTO;
import com.game.staticcontest.Static.Contest.entity.Contest;
import com.game.staticcontest.Static.Contest.service.ContestService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/contests")
public class ContestController {

    @Autowired
    private ContestService contestService;

    @PostMapping("/")
    public ResponseDTO<ContestDTO> addContest(@RequestBody RequestDTO<ContestDTO> requestDTO) {
       ResponseDTO<ContestDTO> responseDTO;
        try {
            if (verifyUser(requestDTO.getUserId())) {
                Contest contest = new Contest();
                contest.setActive(false);
                ContestDTO contestDTO = requestDTO.getRequest();
                BeanUtils.copyProperties(contestDTO, contest);
                responseDTO=contestService.addContest(contest,requestDTO.getUserId());

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


    @PostMapping("/getAll")
    public ResponseDTO<List<ContestDTO>> getAllContest(@RequestBody RequestDTO<Void> requestDTO) {
        ResponseDTO<List<ContestDTO>> responseDTO;
        try {
            if (verifyUser(requestDTO.getUserId())) {

                responseDTO = contestService.getAllContest();
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


    @PostMapping("/{contestId}")
    public ResponseDTO<ContestDTO> getContest(@PathVariable String contestId,@RequestBody RequestDTO<Void> requestDTO) {
        ResponseDTO<ContestDTO> responseDTO;
        try {
            if (verifyUser(requestDTO.getUserId())) {

                responseDTO = contestService.getContest(contestId,requestDTO.getUserId());
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




    public boolean verifyUser(String userId) {
        return true;
    }
}
