package com.game.staticcontest.Static.Contest.thread;

import com.recommendation.kafka_sdk.contest.PlayQuestionKafkaProducer;
import com.recommendation.kafka_sdk.contest.SubscribeKafkaProducer;
import com.recommendation.kafka_sdk.dto.PlayQuestionKafkaMessage;
import com.recommendation.kafka_sdk.dto.SubscribeContestKafkaMessage;
import org.springframework.beans.factory.annotation.Autowired;


public class KafkaSubscriptionThread extends Thread {



    private PlayQuestionKafkaProducer playQuestionKafkaProducer;

    private String userId;
    private String categoryId;

    public KafkaSubscriptionThread(String userId, String categoryId,PlayQuestionKafkaProducer playQuestionKafkaProducer) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.playQuestionKafkaProducer=playQuestionKafkaProducer;
    }

    @Override
    public void run() {
        PlayQuestionKafkaMessage playQuestionKafkaMessage = new PlayQuestionKafkaMessage();
        playQuestionKafkaMessage.setUserId(userId);
        playQuestionKafkaMessage.setCategory(categoryId);
        playQuestionKafkaMessage.setTimestamp(System.nanoTime());
        playQuestionKafkaProducer.sendPlayQuestionKafkaMessage(playQuestionKafkaMessage);
    }
}
