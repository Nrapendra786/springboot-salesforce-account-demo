package com.nrapendra.account.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrapendra.account.exceptions.AccountException;
import com.nrapendra.account.exceptions.ErrorMessages;
import com.nrapendra.account.models.Account;
import com.nrapendra.salesforce.SalesforceConnector;
import com.nrapendra.salesforce.SalesforceObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import static com.nrapendra.account.exceptions.ErrorMessages.BAD_REQUEST;
import static com.nrapendra.account.utils.AppUtil.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountSalesforceService {

    private final SalesforceAuthService authService;

    private final SalesforceConnector salesforceConnector;

    public String createAccount(@RequestBody Account account) throws IOException {
        SalesforceObject salesforceObject = authService.getSalesforceObject();
        var post = salesforceConnector.createAccount(salesforceObject, account);

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(post)) {
            checkResponseCode(response.getStatusLine().getStatusCode());
            return EntityUtils.toString(response.getEntity());
        }
    }

    public String findAccountById(String accountId) throws IOException {
        SalesforceObject salesforceObject = authService.getSalesforceObject();
        var get = salesforceConnector.findAccountById(salesforceObject, accountId);

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(get)) {
            checkResponseCode(response.getStatusLine().getStatusCode());
            return EntityUtils.toString(response.getEntity());
        }
    }

    public String findAccountByName(String name) throws IOException, URISyntaxException {
        SalesforceObject salesforceObject = authService.getSalesforceObject();
        String query = "SELECT Id,Name from ACCOUNT where Name='" + name + "'";

        var get = salesforceConnector.findAccountByQuery(salesforceObject, query);
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(get)) {
            checkResponseCode(response.getStatusLine().getStatusCode());
            return EntityUtils.toString(response.getEntity());
        }
    }

    public String updateAccount(String accountId, Account account) throws IOException {
        SalesforceObject salesforceObject = authService.getSalesforceObject();
        var patch = salesforceConnector.updateAccount(salesforceObject, accountId, account);

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(patch)) {
            checkResponseCode(response.getStatusLine().getStatusCode());
            return responseMessage(accountId, response.getStatusLine().getStatusCode(), account);
        }
    }

    public String deleteAccount(String accountId) throws IOException {
        SalesforceObject salesforceObject = authService.getSalesforceObject();
        var delete = salesforceConnector.deleteAccountById(salesforceObject, accountId);

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(delete)) {
            int responseCode = response.getStatusLine().getStatusCode();
            checkResponseCode(responseCode);
            return responseMessage(accountId, response.getStatusLine().getStatusCode(), DELETE_REQUEST);
        }
    }

    private void checkResponseCode(int responseCode) {
        if (responseCode == HttpStatus.NOT_FOUND.value()) {
            throw new AccountException(ErrorMessages.NOT_FOUND);
        } else if (responseCode >= HttpStatus.BAD_REQUEST.value() && responseCode <= FOUR_NINETY_NINE) {
            throw new AccountException(BAD_REQUEST);
        } else if (responseCode >= HttpStatus.INTERNAL_SERVER_ERROR.value() && responseCode <= FIVE_NINETY_NINE) {
            throw new AccountException(ErrorMessages.INTERNAL_SERVER);
        }
    }

    private String responseMessage(String accountId, int statusCode, Object message) {
        var map = new HashMap<>();
        map.put(ID, accountId);
        map.put(MESSAGE, message);
        map.put(STATUS_CODE, String.valueOf(statusCode));

        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }
}
      
      
      

    
    
    

