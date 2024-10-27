package com.nrapendra.salesforce;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrapendra.account.config.SalesforceConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.nrapendra.account.utils.AppUtil.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalesforceAuthService {

    private final SalesforceConfig salesforceConfig;

    public SalesforceObject getSalesforceObject() throws IOException {
        log.info("USER and TOKEN_URL are {} {} ", salesforceConfig.getUser(), salesforceConfig.getTokenUrl());
    
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(salesforceConfig.getTokenUrl());
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(GRANT_TYPE, GRANT_TYPE_VALUE));
        params.add(new BasicNameValuePair(CLIENT_ID, salesforceConfig.getClientId()));
        params.add(new BasicNameValuePair(CLIENT_SECRET, salesforceConfig.getClientSecret()));
        params.add(new BasicNameValuePair(USERNAME, salesforceConfig.getUser()));
        params.add(new BasicNameValuePair(PASSWORD, salesforceConfig.getPassword() + salesforceConfig.getSecurityToken()));

        post.setEntity(new UrlEncodedFormEntity(params));
        HttpResponse response = client.execute(post);
        String responseBody = EntityUtils.toString(response.getEntity());
        log.debug("RESPONSE BODY IS : {}",responseBody);
        JsonNode json = new ObjectMapper().readTree(responseBody);
        var salesforceObject = new SalesforceObject();
        salesforceObject.setAccessToken(json.get(ACCESS_TOKEN).asText());
        salesforceObject.setInstanceUrl(json.get(INSTANCE_URL).asText());
        return salesforceObject;
    }
}
