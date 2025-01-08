package com.example.keycloack.auth;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpSession;

import java.util.Map;


@RequestMapping("/token")
@RestController
public class TokenController {


  @PostMapping("/")
  public ResponseEntity<String> token(@RequestBody User user, HttpSession session ) throws JsonProcessingException {

    HttpHeaders httpHeaders = new HttpHeaders();
    RestTemplate rt = new RestTemplate();
    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    System.out.println(user);

    MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
    formData.add("client_id", user.clientId);
    formData.add("username", user.username);
    formData.add("password", user.password);
    formData.add("grant_type", user.grantType);

    // conversão dos valores
    HttpEntity<MultiValueMap<String, String>> entity =
    new HttpEntity<MultiValueMap<String,String>>(formData, httpHeaders);

   var result =  rt.postForEntity("http://127.0.0.1:8080/realms/youtube/protocol/openid-connect/token", entity, String.class);

      ObjectMapper objectMapper = new ObjectMapper();
      Map<String, Object> responseMap = objectMapper.readValue(result.getBody(), Map.class);

      String accessToken = (String) responseMap.get("access_token");


      session.setAttribute("nosso_access_token", accessToken); // access token está aqui em accessToken

      // Imprimir o access_token armazenado
      System.out.println("Access Token: " + accessToken);

      session.setAttribute("access_token", result.getBody());

//    System.out.println(session.getAttribute("access_token"));

   return result;

  }

  public record User(String password, String clientId, String grantType, String username) {

  }

}
