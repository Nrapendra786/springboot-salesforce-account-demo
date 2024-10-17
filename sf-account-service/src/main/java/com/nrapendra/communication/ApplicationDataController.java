package com.nrapendra.communication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/v1/application")
@RequiredArgsConstructor
@Slf4j
public class ApplicationDataController {

    private final ApplicationDataRepository applicationDataRepository;

    @GetMapping("/")
    public ResponseEntity<?> getApplicationData() {
        log.info("GET APPLICATION DATA IS INVOKED");
        var response = applicationDataRepository.findByIdAndHttpStatusCodeRequestAndResponse();
        var httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(response,httpStatus);
    }
}
