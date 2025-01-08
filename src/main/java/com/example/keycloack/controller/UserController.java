package com.example.keycloack.controller;


import com.example.keycloack.auth.TokenController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {


    @PostMapping("/")
    public String create(HttpSession sec) throws JsonProcessingException {


        RestTemplate restTemplate = new RestTemplate();
        TokenController.User user = new TokenController.User("user","app_youtube", "password", "user_youtube");
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        HttpEntity<TokenController.User> entity = new HttpEntity<>(user, headers);


//        String tokenJSON = (String) sec.getAttribute("nosso_access_token");
        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8081/token/",
                HttpMethod.POST,
                entity,
                String.class
        );

        String tokenJSON = response.getBody();

        Map<String, Object> responseMap = objectMapper.readValue(tokenJSON, Map.class);
        String accessToken = (String) responseMap.get("access_token");

        var tokenText = (String) sec.getAttribute("nosso_access_token");

        return accessToken;
    }

}
