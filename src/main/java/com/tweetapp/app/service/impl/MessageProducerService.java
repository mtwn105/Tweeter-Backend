package com.tweetapp.app.service.impl;

import com.tweetapp.app.dao.entity.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
public class MessageProducerService {

    @Value("${kafka.topic-name}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, Tweet> kafkaTemplate;

    public void sendTweet(Tweet message) {
        log.info("Sending Tweet to Kafka -> {}", message);
        this.kafkaTemplate.send(topic, message);
    }
}