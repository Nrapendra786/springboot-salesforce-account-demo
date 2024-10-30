package com.nrapendra.integrationtest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrapendra.AccountServiceApplication;
import com.nrapendra.account.models.Account;
import com.nrapendra.account.services.AccountLocalDBService;
import com.nrapendra.account.services.AccountSalesforceService;
import com.nrapendra.helpers.TestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import static com.nrapendra.account.utils.AppUtil.MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AccountServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@Slf4j
public class AccountControllerIntegrationTest {

    private static String NAME = "name";
    private static String ACCOUNT_ID = "";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountSalesforceService accountService;

    @Autowired
    private AccountLocalDBService accountLocalDBService;

    private String getRootUrl() {
        return "http://localhost:" + port + "/api/v1/accounts/";
    }

    @Test
    @Order(1)
    public void testCreateAccount() throws Exception {
        String url = getRootUrl() + "create/";
        int randomNumber = new Random(1).nextInt(100);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("name", "test_name" + randomNumber)
                .queryParam("accountNumber", "1324435")
                .queryParam("phoneNumber", "89776566")
                .queryParam("billingCity", "ZH")
                .queryParam("billingCountry", "CH")
                .queryParam("industry", "Transportation");

        ResponseEntity<String> postResponse = restTemplate.withBasicAuth(TestUtil.USERNAME, TestUtil.PASSWORD)
                .postForEntity(builder.toUriString(), account(), String.class);
        Map<?, ?> map = mapResponseToMap(postResponse.getBody());
        ACCOUNT_ID = (String) map.get("id");
        assertEquals(postResponse.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    @Order(2)
    public void testFindAccountById() throws JsonProcessingException {
        String url = getRootUrl() + ACCOUNT_ID;
        log.info("testFindAccountById is Invoked and url is : {}", url);
        ResponseEntity<String> getResponse =
                restTemplate
                        .withBasicAuth(TestUtil.USERNAME, TestUtil.PASSWORD)
                        .getForEntity(URI.create(url), String.class);

        Map<?, ?> map = mapResponseToMap(getResponse.getBody());
        NAME = (String) map.get(NAME);
        assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @Order(3)
    public void testFindAccountByName() {
        log.debug("testFindAccountByName is Invoked and NAME is : {} ", NAME);
        ResponseEntity<String> getResponse =
                restTemplate
                        .withBasicAuth(TestUtil.USERNAME, TestUtil.PASSWORD)
                        .getForEntity(URI.create(getRootUrl() + "parameter/" + NAME), String.class);
        assertEquals(getResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @Order(4)
    public void testUpdateAccount() throws JsonProcessingException {
        log.debug("testUpdateAccount is Invoked and ACCOUNT_ID is : {} ", ACCOUNT_ID);
        String url = getRootUrl() + ACCOUNT_ID;

        int randomNumber = new Random(100).nextInt(1, 100);

        String newName = "test_new_name" + randomNumber;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("name", newName);

        ResponseEntity<String> putResponse = restTemplate
                .withBasicAuth(TestUtil.USERNAME, TestUtil.PASSWORD)
                .exchange(builder.toUriString(),
                        HttpMethod.PUT,
                        HttpEntity.EMPTY,
                        String.class);

        assertEquals(putResponse.getStatusCode(), HttpStatus.OK);

        LinkedHashMap<String, String> messageJson = (LinkedHashMap<String, String>) mapResponseToMap(putResponse.getBody()).get(MESSAGE);

        var expectedValue = messageJson.get("name");
        assertEquals(expectedValue, newName);
    }

    @Test
    @Order(5)
    public void testDeleteAccount() {
        log.debug("testDeleteAccount is Invoked and ACCOUNT_ID is : {} ", ACCOUNT_ID);
        ResponseEntity<String> deleteResponse = restTemplate.withBasicAuth(TestUtil.USERNAME, TestUtil.PASSWORD)
                .exchange(URI.create(getRootUrl() + ACCOUNT_ID),
                        HttpMethod.DELETE,
                        HttpEntity.EMPTY,
                        String.class);

        assertEquals(deleteResponse.getStatusCode(), HttpStatus.ACCEPTED);
    }

    private Account account() {
        return Account.builder().name("test_name")
                .accountNumber("1324435")
                .phoneNumber("89776566")
                .billingCity("ZH")
                .billingCountry("CH")
                .industry("Transportation")
                .build();
    }

    private Map mapResponseToMap(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, Map.class);
    }
}

