package com.revature.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AmadeusAuthService {

    @Value("${amadeus.apiKey}")
    private String apiKey;

    @Value("${amadeus.apiSecret}")
    private String apiSecret;

    private final RestTemplate restTemplate;

    public AmadeusAuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAccessToken() {
        return authenticate();
    }

    private String authenticate() {
        String url = "https://test.api.amadeus.com/v1/security/oauth2/token";

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Create the body with the form data
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", apiKey);
        body.add("client_secret", apiSecret);
        body.add("grant_type", "client_credentials");

        // Create the HttpEntity with the headers and body
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        // Make the request
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, String> responseBody = response.getBody();
            if (responseBody != null) {
                return responseBody.get("access_token");
            }
        }

        throw new RuntimeException("Failed to authenticate with Amadeus API");
    }
}
