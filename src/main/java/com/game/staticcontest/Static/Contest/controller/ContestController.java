package com.game.staticcontest.Static.Contest.controller;

import com.game.staticcontest.Static.Contest.dto.ContestDTO;
import com.game.staticcontest.Static.Contest.dto.ResponseDTO;
import com.game.staticcontest.Static.Contest.entity.Contest;
import com.game.staticcontest.Static.Contest.service.ContestService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contests")
public class ContestController {

    @Autowired
    ContestService contestService;

    @PostMapping("/")
    public ResponseDTO<Void> addContest(@RequestBody ContestDTO contestDTO) {
        Contest contest=new Contest();
        BeanUtils.copyProperties(contestDTO,contest);

        return contestService.addContest(contest);
    }
}
