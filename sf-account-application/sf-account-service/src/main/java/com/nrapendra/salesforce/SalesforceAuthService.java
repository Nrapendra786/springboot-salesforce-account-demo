package com.nrapendra.salesforce;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrapendra.account.config.AppConfig;
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

    private final AppConfig appConfig;

    public SalesforceObject getSalesforceObject() throws IOException {
        log.info("USER and TOKEN_URL are {} {} ", appConfig.getUser(),appConfig.getTokenUrl());
        log.info("is TRUE : {}",appConfig.getUser().equals("trivajay259@gmail.com"));
        log.info("is TRUE : {}",appConfig.getPassword().equals("Narendra786n"));
        log.info("is TRUE : {}",appConfig.getClientId().equals("3MVG9YFqzc_KnL.zJzsr.Smd3mUFyV.z2M8bTDbQSv6IKfXjjEqalQ15tjxvXgiXusGMBJybc2yFEZH5FUBCO"));
        log.info("is TRUE : {}",appConfig.getClientSecret().equals("5F31C122D3751268C2A7B17E7E4720B9B4A3E6683A1E3B7F35CD049BA2860D94"));
        log.info("is TRUE : {}",appConfig.getSecurityToken().equals("ZH7SOg1UFgmtcqgPRIBruPX5V"));
        log.info("is TRUE : {}",appConfig.getTokenUrl().equals("https://login.salesforce.com/services/oauth2/token"));
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(appConfig.getTokenUrl());
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair(GRANT_TYPE, GRANT_TYPE_VALUE));
        params.add(new BasicNameValuePair(CLIENT_ID, appConfig.getClientId()));
        params.add(new BasicNameValuePair(CLIENT_SECRET, appConfig.getClientSecret()));
        params.add(new BasicNameValuePair(USERNAME, appConfig.getUser()));
        params.add(new BasicNameValuePair(PASSWORD, appConfig.getPassword() + appConfig.getSecurityToken()));

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
