package com.game.staticcontest.Static.Contest.thread;

import com.contest.notificationProducer.dto.Header;
import com.contest.notificationProducer.notificationEnum.NotificationMedium;
import com.contest.notificationProducer.notificationEnum.NotificationType;
import com.contest.notificationProducer.producer.ContestProducer;
import com.game.staticcontest.Static.Contest.entity.Contest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddContesNotificationThread extends Thread {



    @Autowired
    private ContestProducer contestProducer;

    private Contest contest;
    public AddContesNotificationThread (Contest contest){
        this.contest=contest;
    }


    @Override
    public void run() {
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
