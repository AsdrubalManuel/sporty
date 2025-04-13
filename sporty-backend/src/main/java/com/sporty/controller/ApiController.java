package com.sporty.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user")
    public String protectedEndpoint(JwtAuthenticationToken token) {
        return "Hello " + token.getName();
    }
}
