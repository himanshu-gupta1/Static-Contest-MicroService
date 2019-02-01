package com.game.staticcontest.Static.Contest.thread;

import com.contest.notificationProducer.dto.Header;
import com.contest.notificationProducer.dto.SubscriptionNotice;
import com.contest.notificationProducer.exception.FieldsCanNotBeEmpty;
import com.contest.notificationProducer.notificationEnum.NotificationMedium;
import com.contest.notificationProducer.notificationEnum.NotificationType;
import com.contest.notificationProducer.producer.SubscriptionNoticeProducer;
import com.game.staticcontest.Static.Contest.repository.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubscriptionNotificationThread extends Thread {


    private ContestRepository contestRepository;

    private SubscriptionNoticeProducer subscriptionNoticeProducer;

    private String contestId;
    private String userId;


    public SubscriptionNotificationThread (String contestId, String userId,SubscriptionNoticeProducer subscriptionNoticeProducer,ContestRepository contestRepository){
        this.contestId=contestId;
        this.userId=userId;
        this.subscriptionNoticeProducer=subscriptionNoticeProducer;
        this.contestRepository=contestRepository;
    }


    @Override
    public void run() {

        try {
            Header header = new Header();
            SubscriptionNotice subscriptionNotice = new SubscriptionNotice();
            subscriptionNotice.setContestId(contestId);
            subscriptionNotice.setContestName(contestRepository.findOne(contestId).getName());
            //subscriptionNotice.setFollowerIds(getFollowersFromUserId(userId));
            List<String> list=new ArrayList<>();
            list.add("8a8a7a69-b642-4f7f-a4ef-2b43a370c3fe");
            list.add("20ed6f87-9799-48da-ac39-83df54f56329");
            subscriptionNotice.setFollowerIds(list);
            List<NotificationMedium> notificationMediumList = new ArrayList<>();
            notificationMediumList.add(NotificationMedium.EMAIL);
            header.setNotificationMedium(notificationMediumList);
            header.setNotificationType(NotificationType.SUBSCRIPTION_NOTICE);
            header.setTimeStamp(new Date().toString());
            header.setNotificationTypeBody(subscriptionNotice);
            header.setNotificationMedium(notificationMediumList);
            header.setReceiver(userId);
            System.out.println("User id received from subscription: "+ userId);
            subscriptionNoticeProducer.send(header);

        } catch (FieldsCanNotBeEmpty fieldsCanNotBeEmpty) {
            fieldsCanNotBeEmpty.printStackTrace();
        }

    }
}
