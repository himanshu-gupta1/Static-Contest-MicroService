package com.game.staticcontest.Static.Contest.service.implementation;

import com.game.staticcontest.Static.Contest.dto.ResponseDTO;
import com.game.staticcontest.Static.Contest.entity.ContestPlayArea;
import com.game.staticcontest.Static.Contest.repository.ContestPlayAreaRepository;
import com.game.staticcontest.Static.Contest.service.ContestPlayAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestPlayAreaServiceImplementation implements ContestPlayAreaService {

    @Autowired
    private ContestPlayAreaRepository contestPlayAreaRepository;

    @Override
    public List<ContestPlayArea> getContestPlayArea(String contestId, String userId) {
        return  contestPlayAreaRepository.getContestPlayArea(contestId,userId);

    }

    @Override
    public ContestPlayArea getContestPlayArea(String contestId, String questionId, String userId) {
        return  contestPlayAreaRepository.getContestPlayArea(contestId,questionId,userId);
    }

    @Override
    public String getMaximumQuestionSequence(String contestId, String userId) {
        return contestPlayAreaRepository.getMaximumQuestionSequence(contestId,userId);

    }

    @Override
    public ContestPlayArea addContestPlayArea(ContestPlayArea contestPlayArea) {
        return contestPlayAreaRepository.save(contestPlayArea);
    }

    @Override
    public int getNoOfSkips(String contestId, String userId) {
        return contestPlayAreaRepository.getNoOfSkips(contestId,userId);
    }

}
