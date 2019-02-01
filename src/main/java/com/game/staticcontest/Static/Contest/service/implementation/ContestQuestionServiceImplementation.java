package com.game.staticcontest.Static.Contest.service.implementation;

import com.contest.notificationProducer.dto.Header;
import com.contest.notificationProducer.notificationEnum.NotificationMedium;
import com.contest.notificationProducer.notificationEnum.NotificationType;
import com.contest.notificationProducer.producer.ContestProducer;
import com.game.staticcontest.Static.Contest.dto.QuestionDetailDTO;
import com.game.staticcontest.Static.Contest.dto.ResponseDTO;
import com.game.staticcontest.Static.Contest.entity.Contest;
import com.game.staticcontest.Static.Contest.entity.ContestQuestion;
import com.game.staticcontest.Static.Contest.repository.ContestQuestionRepository;
import com.game.staticcontest.Static.Contest.repository.ContestRepository;
import com.game.staticcontest.Static.Contest.service.ContestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class ContestQuestionServiceImplementation implements ContestQuestionService {

    @Autowired
    private ContestQuestionRepository contestQuestionRepository;


    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private ContestProducer contestProducer;

    public ResponseDTO<Void> addQuestion(ContestQuestion contestQuestion) {
        contestQuestionRepository.save(contestQuestion);
        ResponseDTO<Void> responseDTO = new ResponseDTO<>();
        responseDTO.setStatus("success");
        responseDTO.setErrorMessage("");
        responseDTO.setResponse(null);

        return responseDTO;
    }

    @Override
    public ContestQuestion findByQuestionSequence(int questionSequence,String contestId) {

        return contestQuestionRepository.findContestQuestionByQuestionSequenceAndContestId(questionSequence,contestId);
    }



    @Override
    public ResponseDTO<Void> addQuestionToContest(String contestId, List<QuestionDetailDTO> questionDetailDTOList) {
        ResponseDTO<Void> responseDTO=new ResponseDTO<>();
        if (checkQuestions(questionDetailDTOList)) {
            int sequence = 1;
            for (QuestionDetailDTO questionDetailDTO : questionDetailDTOList) {

                Contest contest=new Contest();
                ContestQuestion contestQuestion=new ContestQuestion();
                contest.setContestId(contestId);
                contestQuestion.setContest(contest);
                contestQuestion.setQuestionId(questionDetailDTO.getQuestionId());
                contestQuestion.setQuestionSequence((sequence++));
                addQuestion(contestQuestion);

                //sending notification....
                Contest contestGet=contestRepository.findOne(contestId);
                sendNotification(contestGet);
                //..................

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


        }
        return responseDTO;
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



    public void sendNotification(Contest contest)
    {


        try {
            Header header = new Header();
            com.contest.notificationProducer.dto.Contest contestN = new com.contest.notificationProducer.dto.Contest();
            contestN.setContestId(contest.getContestId());
            contestN.setContestName(contest.getName());
            List<NotificationMedium> notificationMediumList = new ArrayList<>();
            notificationMediumList.add(NotificationMedium.EMAIL);
            header.setNotificationMedium(notificationMediumList);
            header.setNotificationType(NotificationType.CONTEST);
            header.setTimeStamp(new Date().toString());
            header.setNotificationTypeBody(contestN);
            header.setReceiver("");
            contestProducer.send(header);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }


    }


}
