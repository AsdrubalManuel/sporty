package com.sporty.controller;

import com.sporty.controller.dto.ShoppingCart;
import com.sporty.controller.dto.ShoppingCartResponse;
import com.sporty.exception.UnauthorizedException;
import com.sporty.model.AuthUser;
import com.sporty.repository.AuthUserRepository;
import com.sporty.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private AuthUserRepository authUserRepository;

    @PostMapping("/calculate")
    public ShoppingCartResponse calculateShoppingCartCost(JwtAuthenticationToken token, @RequestBody ShoppingCart shoppingCart) {
        Optional<AuthUser> user = authUserRepository.findByEmail((String) token.getTokenAttributes().get("email"));

        if (user.isEmpty()) {
            throw new UnauthorizedException();
        }

        return orderService.calculateShoppingCartCost(user.get(), shoppingCart);
    }
}
