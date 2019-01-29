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
    public ResponseDTO<ContestSubscribedDTO> subscribe(@PathVariable String contestId, @RequestBody RequestDTO<Void> requestDTO) {

        try {
            if (verifyUser(requestDTO.getUserId())) {
                return contestSubscribedService.subscribe(contestId,requestDTO.getUserId());

            } else {
                ResponseDTO<ContestSubscribedDTO> responseDTO = new ResponseDTO<>();
                responseDTO.setStatus("Auth Failed");
                responseDTO.setErrorMessage("");
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




    @PutMapping("/finished")
    public ResponseDTO<Void> finish(@PathVariable String contestId,@RequestBody RequestDTO<Void> requestDTO) {

        try {
            if (verifyUser(requestDTO.getUserId())) {
                return contestSubscribedService.finish(contestId,requestDTO.getUserId());


            } else {
                ResponseDTO<Void> responseDTO = new ResponseDTO<>();
                responseDTO.setStatus("Auth Failed");
                responseDTO.setErrorMessage("");
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

    }









    public boolean verifyUser(String userId) {
        return true;
    }


}
