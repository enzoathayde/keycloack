package com.example.keycloack.auth;



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

@RequestMapping("/token")
@RestController
public class TokenController {
  

  @PostMapping("/")
  public ResponseEntity<String> token(@RequestBody User user ) {

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

   return result;

  }

  public record User(String password, String clientId, String grantType, String username) {
   
  }

}
