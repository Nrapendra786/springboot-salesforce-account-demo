package com.nrapendra.consumer.service;

import com.nrapendra.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final EventService eventService;

    @KafkaListener(topics = {"my_topic"}, groupId = "group_id")
    public void consume(@Payload Message message) {
        log.info(String.format("Received: " + message));
        eventService.saveEvent(message);
    }
}
