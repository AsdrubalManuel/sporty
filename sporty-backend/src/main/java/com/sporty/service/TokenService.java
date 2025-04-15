package com.sporty.service;

import com.sporty.exception.UnauthorizedException;
import com.sporty.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;

import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class TokenService {
    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthUserService authUserService;

    @Value("${jwt.expiration.seconds:36000}")
    private int expirationInSeconds;

    public String login(String email, String password) {

        Optional<AuthUser> user = authUserService.getUser(email);
        if(user.isEmpty()) {
            throw new UnauthorizedException();
        }

        if(!bCryptPasswordEncoder.matches(password, user.get().getPassword())) {
            throw new UnauthorizedException();
        }

        var now = Instant.now();
        var claims = JwtClaimsSet.builder()
                .issuer("sporty.com")
                .claim("email", user.get().getEmail())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expirationInSeconds))
                .claim("scope", "default scope")
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return jwtValue;
    }
}
