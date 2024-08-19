package com.revature.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

@Service
public class AmadeusAuthService {

    @Value("${amadeus.apiKey}")
    private String apiKey;

    @Value("${amadeus.apiSecret}")
    private String apiSecret;

    private final RestTemplate restTemplate;

    private String accessToken;

    public AmadeusAuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAccessToken() {
        if (accessToken == null || isTokenExpired()) {
            authenticate();
        }
        return accessToken;
    }

    private void authenticate() {
        String url = "https://test.api.amadeus.com/v1/security/oauth2/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        Map<String, String> bodyParams = new HashMap<>();
        bodyParams.put("grant_type", "client_credentials");
        bodyParams.put("client_id", apiKey);
        bodyParams.put("client_secret", apiSecret);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(bodyParams, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();
            this.accessToken = (String) responseBody.get("access_token");
            // Optionally, you can store the token expiration time and refresh it when necessary
        }
    }

    private boolean isTokenExpired() {
        // Implement token expiration logic if needed, or always refresh the token for simplicity
        return false;
    }
}
