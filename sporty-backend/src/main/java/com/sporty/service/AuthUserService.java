package com.sporty.service;

import com.sporty.model.AuthUser;
import com.sporty.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserService {

    @Autowired
    private AuthUserRepository authUserRepository;

    public Optional<AuthUser> getUser(String email) {
        return authUserRepository.findByEmail(email);
    }

}
