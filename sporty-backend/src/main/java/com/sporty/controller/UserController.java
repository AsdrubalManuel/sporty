package com.sporty.controller;

import com.sporty.model.AuthUser;
import com.sporty.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private AuthUserRepository authUserRepository;

    @GetMapping("/user")
    public Map protectedEndpoint(JwtAuthenticationToken token) {
        Optional<AuthUser> user = authUserRepository.findByEmail((String) token.getTokenAttributes().get("email"));

        return Map.of("email",user.get().getEmail(), "loyaltyPoints", user.get().getLoyaltyPoints(), "admin", user.get().isAdmin());
    }
}
