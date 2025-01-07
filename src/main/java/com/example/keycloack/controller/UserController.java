package com.example.keycloack.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {


    @PostMapping("/")
    public String create(HttpSession sec) {


        String tokenJSON = (String) sec.getAttribute("nosso_access_token");

        ObjectMapper objectMapper = new ObjectMapper();


        return tokenJSON;
    }

}
