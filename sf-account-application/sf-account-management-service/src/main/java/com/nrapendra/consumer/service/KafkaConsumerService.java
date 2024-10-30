package com.nrapendra.consumer.service;

import com.nrapendra.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import static com.nrapendra.utils.AppUtil.TOPIC_NAME;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

    private final EventService eventService;

    @KafkaListener(topics = {TOPIC_NAME }, groupId = "group_id")
    public void consume(@Payload Message message, Acknowledgment acknowledgment) {
        log.info(String.format("Received: " + message));
        eventService.saveEvent(message);
        acknowledgment.acknowledge();
    }
}
