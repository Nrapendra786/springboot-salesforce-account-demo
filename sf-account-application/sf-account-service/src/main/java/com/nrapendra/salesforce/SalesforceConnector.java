package com.nrapendra.salesforce;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrapendra.account.models.Account;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.nrapendra.account.utils.AppUtil.*;

@Component
public class SalesforceConnector {

    public HttpRequestBase createAccount(SalesforceObject salesforceObject, Account account) throws JsonProcessingException, UnsupportedEncodingException {

        var accessToken = salesforceObject.getAccessToken();
        var salesforceInstanceUrl = salesforceObject.getInstanceUrl();

        var url = salesforceInstanceUrl + SALESFORCE_ACCOUNT_URL;
        var post = new HttpPost(url);
        post.setHeader(AUTHORIZATION, BEARER + accessToken);
        post.setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        var objectMapper = new ObjectMapper();
        var accountData = createAccount(account);

        var json = objectMapper.writeValueAsString(accountData);
        post.setEntity(new StringEntity(json));
        return post;
    }

    public HttpRequestBase findAccountById(SalesforceObject salesforceObject, String accountId){
        validateAccountId(accountId);
        var accessToken = salesforceObject.getAccessToken();
        var salesforceInstanceUrl = salesforceObject.getInstanceUrl();

        var url = salesforceInstanceUrl + SALESFORCE_ACCOUNT_URL;

        var get = new HttpGet(url + "/" + accountId);
        get.setHeader(AUTHORIZATION, BEARER + accessToken);
        get.setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return get;
    }

    public HttpRequestBase findAccountByQuery(SalesforceObject salesforceObject, String query){
        var accessToken = salesforceObject.getAccessToken();
        var salesforceInstanceUrl = salesforceObject.getInstanceUrl();

        var encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        final String baseUrl = salesforceInstanceUrl + SALESFORCE_QUERY_URL + encodedQuery;

        var get = new HttpGet(baseUrl);
        get.setHeader(AUTHORIZATION, BEARER + accessToken);
        get.setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return get;
    }

    public HttpRequestBase deleteAccountById(SalesforceObject salesforceObject, String accountId){
        validateAccountId(accountId);
        var accessToken = salesforceObject.getAccessToken();
        var salesforceInstanceUrl = salesforceObject.getInstanceUrl();

        var url = salesforceInstanceUrl + SALESFORCE_ACCOUNT_URL;

        var delete = new HttpDelete(url + "/" + accountId);
        delete.setHeader(AUTHORIZATION, BEARER + accessToken);
        delete.setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return delete;
    }

    public HttpRequestBase updateAccount(SalesforceObject salesforceObject,String accountId, Account account) throws UnsupportedEncodingException, JsonProcessingException {
        validateAccountId(accountId);
        var accessToken = salesforceObject.getAccessToken();
        var salesforceInstanceUrl = salesforceObject.getInstanceUrl();

        var url = salesforceInstanceUrl + SALESFORCE_ACCOUNT_URL;

        var patch = new HttpPatch(url  + accountId);
        patch.setHeader(AUTHORIZATION, BEARER + accessToken);
        patch.setHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        var objectMapper = new ObjectMapper();

        var accountData = createAccount(account);
        var json = objectMapper.writeValueAsString(accountData);
        patch.setEntity(new StringEntity(json));
        return patch;
    }

    private Map<String,String> createAccount(Account account){

        var accountData = new HashMap<String,String>();

        if(isNotNullNorEmpty(account.getName()))
           accountData.put(NAME, account.getName());

        if(isNotNullNorEmpty(account.getAccountNumber()) )
           accountData.put(ACCOUNT_NUMBER, account.getAccountNumber());

        if(isNotNullNorEmpty(account.getIndustry()))
           accountData.put(INDUSTRY, account.getIndustry());

        if(isNotNullNorEmpty(account.getBillingCity()))
           accountData.put(BILLING_CITY, account.getBillingCity());

        if(isNotNullNorEmpty(account.getPhoneNumber()))
           accountData.put(PHONE,account.getPhoneNumber());

        return accountData;
    }

    private boolean isNotNullNorEmpty(String value) {
        return Objects.nonNull(value)  &&  !value.isBlank();
    }

    private void validateAccountId(String accountId) {
        if (!accountId.matches("^[a-zA-Z0-9]{15}|[a-zA-Z0-9]{18}$")) {
            throw new IllegalArgumentException("Invalid Salesforce account ID");
        }
    }
}

