package com.sporty.controller.dto;

import com.sporty.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ShoppingCartResponse {
    List<BookResponse> orderedBooks;
    Double totalCost;
}
