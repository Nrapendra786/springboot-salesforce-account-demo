package com.nrapendra.consumer.controllers;

import com.nrapendra.consumer.events.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class EventController {

    private final EventRepository eventRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllEvents() {
        log.info("getAllEvents is Invoked");
        var response = eventRepository.findAll();
        var httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(response, httpStatus);
    }



}
