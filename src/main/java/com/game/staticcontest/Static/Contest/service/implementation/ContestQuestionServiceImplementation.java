package com.game.staticcontest.Static.Contest.service.implementation;

import com.game.staticcontest.Static.Contest.dto.ResponseDTO;
import com.game.staticcontest.Static.Contest.entity.ContestQuestion;
import com.game.staticcontest.Static.Contest.repository.ContestQuestionRepository;
import com.game.staticcontest.Static.Contest.service.ContestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class ContestQuestionServiceImplementation implements ContestQuestionService {

    @Autowired
    private ContestQuestionRepository contestQuestionRepository;

    public ResponseDTO<Void> addQuestion(ContestQuestion contestQuestion) {
        contestQuestionRepository.save(contestQuestion);
        ResponseDTO<Void> responseDTO = new ResponseDTO<>();
        responseDTO.setStatus("success");
        responseDTO.setErrorMessage("");
        responseDTO.setResponse(null);

        return responseDTO;
    }

    @Override
    public ContestQuestion findByQuestionSequence(int questionSequence) {

        return contestQuestionRepository.findByQuestionSequence(questionSequence);
    }


}
