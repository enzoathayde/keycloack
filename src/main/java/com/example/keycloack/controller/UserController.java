package com.example.keycloack.controller;


import com.example.keycloack.auth.TokenController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {


    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody Usuario usuario, HttpSession sec) throws JsonProcessingException {


        RestTemplate restTemplate = new RestTemplate();
        TokenController.User user = new TokenController.User("admin","app_youtube", "password", "admin_youtube");
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        HttpEntity<TokenController.User> entity = new HttpEntity<>(user, headers);
        RestTemplate restPostUserTemplate = new RestTemplate();

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

        System.out.println(accessToken);


        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setContentType(MediaType.APPLICATION_JSON);
        userHeaders.set("Authorization", "Bearer " + accessToken);
        System.out.println(usuario);

        HttpEntity<Usuario> userEntity = new HttpEntity<>(usuario,userHeaders);

        System.out.println(userEntity);

        String gould = "";

        ResponseEntity<String> resp = restPostUserTemplate.exchange("http://127.0.0.1:8080/admin/realms/youtube/users",
                HttpMethod.POST,
                userEntity,
                String.class);




       return new ResponseEntity<String>("Cadastro concluído", HttpStatus.OK);

    }

    public record Usuario(String username, Boolean enabled, String email, String firstName, String lastName, List<Credential> credentials) {

    }

    public record Credential(String type, String value, Boolean temporary) {
    }


}
