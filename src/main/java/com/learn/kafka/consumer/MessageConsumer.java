package com.learn.kafka.consumer;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static org.apache.kafka.common.requests.DeleteAclsResponse.log;

@Component
@Slf4j
public class MessageConsumer {

    @KafkaListener(topics = "mon-tunnel-topic", groupId = "message-group-1")
    public void listen(String message) {
        log.info("Message receive : {}", message);
    }

}