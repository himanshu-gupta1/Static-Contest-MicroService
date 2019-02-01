package com.game.staticcontest.Static.Contest.thread;

import com.recommendation.kafka_sdk.contest.SubscribeKafkaProducer;
import com.recommendation.kafka_sdk.dto.SubscribeContestKafkaMessage;
import org.springframework.beans.factory.annotation.Autowired;

public class KafkaSubscriptionThread extends Thread {

    @Autowired
    SubscribeKafkaProducer subscribeKafkaProducer;

    private String userId;
    private String categoryId;

    public KafkaSubscriptionThread(String userId, String categoryId) {
        this.userId = userId;
        this.categoryId = categoryId;
    }

    @Override
    public void run() {
        try {
            SubscribeContestKafkaMessage subscribeContestKafkaMessage = new SubscribeContestKafkaMessage();
            subscribeContestKafkaMessage.setUserId(userId);


            subscribeContestKafkaMessage.setCategory(categoryId);
            subscribeContestKafkaMessage.setTimestamp(System.nanoTime());

            subscribeKafkaProducer.sendSubscribeKafkaMessage(subscribeContestKafkaMessage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
