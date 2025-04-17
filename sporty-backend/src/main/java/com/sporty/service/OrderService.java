package com.sporty.service;

import com.sporty.controller.dto.ShoppingCart;
import com.sporty.controller.dto.ShoppingCartResponse;
import com.sporty.model.AuthUser;
import com.sporty.model.Book;
import com.sporty.repository.AuthUserRepository;
import com.sporty.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthUserRepository authUserRepository;
    public ShoppingCartResponse calculateShoppingCartCost(AuthUser user, ShoppingCart shoppingCart) {
        Set<Long> bookIds = shoppingCart.books.entrySet()
                .stream()
                .filter(e -> e.getValue() > 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());;
                
        List<Book> books = bookRepository.findAllByIdIn(bookIds);

        //Add entries if there is the same book is requested more than once
        List<Book> repeatedRequests = new ArrayList<>();
        books.stream().forEach(book -> {
            int count = shoppingCart.getBooks().get(book.getId());
            if (count > 1) {
                for (int i = 1; i < count; i++) {
                    repeatedRequests.add(book.clone());
                }
            }
        });

        books.addAll(repeatedRequests);


        //Apply base discounts
        inventoryService.applyDiscounts(books);

        //Apply loyalty points
        if (shoppingCart.useLoyaltyPoints && user.getLoyaltyPoints() >= 10) {
            boolean applied = inventoryService.applyLoyaltyPointsDiscount(books);
            if(applied) user.setLoyaltyPoints(user.getLoyaltyPoints() - 10);
            authUserRepository.save(user);
        }

        Double totalPrice = books.stream()
                .mapToDouble(b -> b.getPrice() -  b.getPrice() * b.getDiscount())
                .sum();


        return new ShoppingCartResponse(inventoryService.toBookResponse(books.stream()),totalPrice);
    }

}
