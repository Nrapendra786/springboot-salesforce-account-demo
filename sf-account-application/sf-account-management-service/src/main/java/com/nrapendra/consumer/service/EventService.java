package com.nrapendra.consumer.service;

import com.nrapendra.Message;
import com.nrapendra.consumer.events.Event;
import com.nrapendra.consumer.events.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {

    private final EventRepository eventRepository;

    @Transactional(propagation= Propagation.REQUIRES_NEW)
    public void saveEvent(Message message){
        var event = Event.builder().schema_id(message.getSchema_id()).payload(message.getPayload()).build();
        eventRepository.save(event);
    }
}
