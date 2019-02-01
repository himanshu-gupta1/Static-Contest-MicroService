package com.game.staticcontest.Static.Contest.controller;


import com.game.staticcontest.Static.Contest.dto.ContestSubscribedDTO;
import com.game.staticcontest.Static.Contest.dto.RequestDTO;
import com.game.staticcontest.Static.Contest.dto.ResponseDTO;
import com.game.staticcontest.Static.Contest.service.ContestSubscribedService;
import org.omg.CORBA.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contests/{contestId}")
@CrossOrigin
public class ContestSubscriptionController {


    @Autowired
    private ContestSubscribedService contestSubscribedService;



    @PostMapping("/subscribe")
    public ResponseDTO<ContestSubscribedDTO> subscribe(@PathVariable("contestId") String contestId, @RequestBody RequestDTO<Void> requestDTO) {
        ResponseDTO<ContestSubscribedDTO> responseDTO;
        try {
            if (verifyUser(requestDTO.getUserId())) {
                responseDTO = contestSubscribedService.subscribe(contestId,requestDTO.getUserId());

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




    @PutMapping("/finished")
    public ResponseDTO<ContestSubscribedDTO> finish(@PathVariable String contestId,@RequestBody RequestDTO<Void> requestDTO) {

        System.out.println("finish method called");
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









    public boolean verifyUser(String userId) {
        return true;
    }


}
