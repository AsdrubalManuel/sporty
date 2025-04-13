package com.sporty.controller;

import com.sporty.controller.dto.LoginRequest;
import com.sporty.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        var token = tokenService.login(loginRequest.getEmail(), loginRequest.getPassword());

        return ResponseEntity.ok(Map.of("access_token", token));
    }
}
