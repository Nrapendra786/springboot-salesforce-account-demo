package com.nrapendra.account.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrapendra.account.config.AppConfig;
import com.nrapendra.account.exceptions.AccountException;
import com.nrapendra.account.exceptions.ErrorMessages;
import com.nrapendra.account.models.Account;
import com.nrapendra.account.services.AccountLocalDBService;
import com.nrapendra.account.services.AccountSalesforceService;
import com.nrapendra.communication.ApplicationData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;

/**
 * TODO validation of input Data
 */

@RestController
@RequestMapping(value = "/api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController extends OpenAPIController {

    private final AccountSalesforceService accountService;

    private final AccountLocalDBService accountLocalDBService;



    @PostMapping("/create/")
    public ResponseEntity<?> createAccount(@RequestParam("name") String name,
                                           @RequestParam("accountNumber") String accountNumber,
                                           @RequestParam("phoneNumber") String phoneNumber,
                                           @RequestParam("billingCity") String billingCity,
                                           @RequestParam("billingCountry") String billingCountry,
                                           @RequestParam("industry") String industry
    ) throws IOException {
        log.info("createAccount is Invoked");
        var account = Account.builder()
                .name(name)
                .accountNumber(accountNumber)
                .industry(phoneNumber)
                .phoneNumber(phoneNumber)
                .billingCity(billingCity)
                .billingCountry(billingCountry)
                .industry(industry).build();
        var response = accountService.createAccount(account);
        var httpStatus = HttpStatus.CREATED;
        var request = account.toString();
        saveToLocalDB(request, response, httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable String id) throws IOException {
        log.info("getAccountById is Invoked");
        var response = accountService.findAccountById(id);
        var httpStatus = HttpStatus.OK;
        saveToLocalDB(id, response, httpStatus);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable String id,
                                           @RequestParam("name") String name
    ) throws IOException {
        log.info("updateAccount is Invoked");
        var account = Account.builder()
                .name(name)
                .accountNumber("TEST_ACC")
                .industry("TEST_INDUSTRY")
                .phoneNumber("+9494040404")
                .billingCity("ZH")
                .billingCountry("Switzerland")
                .industry("TSP").build();
        var response = accountService.updateAccount(id, account);
        var httpStatus = HttpStatus.OK;
        var request = account.toString();
        saveToLocalDB(request, response, httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable String id) throws IOException {
        log.info("deleteAccount is Invoked");
        var response = accountService.deleteAccount(id);
        var httpStatus = HttpStatus.ACCEPTED;
        saveToLocalDB(id, response, httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/parameter/{name}")
    public ResponseEntity<?> findAccountByName(@PathVariable String name) throws IOException {
        log.info("findAccountByName is Invoked");
        String response;
        try {
            response = accountService.findAccountByName(name);
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
            throw new AccountException(ErrorMessages.BAD_REQUEST);
        }
        var httpStatus = HttpStatus.OK;
        saveToLocalDB(name, response, httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }

    private JsonNode mapResponseToJsonNode(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, JsonNode.class);
    }

    private void saveToLocalDB(String request, String response, HttpStatus httpStatus) throws JsonProcessingException {
        long positiveUniqueUUID = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        JsonNode jsonNode = mapResponseToJsonNode(response);

        Map<Object,Object> map = new ObjectMapper().convertValue(jsonNode, Map.class);

        var applicationData = ApplicationData.builder().id(positiveUniqueUUID).request(request).response(map).httpResponseCode(httpStatus.value()).build();
        accountLocalDBService.saveApplicationDataToRepository(applicationData);
    }
}
