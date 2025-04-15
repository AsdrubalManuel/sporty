package com.sporty.service;

import com.sporty.controller.dto.BookPageResponse;
import com.sporty.controller.dto.BookResponse;
import com.sporty.controller.dto.PageResponse;
import com.sporty.model.Book;
import com.sporty.repository.BookRepository;
import com.sporty.service.discount.DiscountChainNode;
import com.sporty.service.discount.TemporalDiscountChainNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private static final int PAGE_SIZE = 10;
    private DiscountChainNode discountChainNode;

    @Autowired
    private BookRepository bookRepository;

    public InventoryService() {
        //Initialize discount chain

        this.discountChainNode = new TemporalDiscountChainNode(
                i-> i.isAfter(Instant.now().minus(Duration.ofDays(365 * 5))),
                i-> i.isBefore(Instant.now().minus(Duration.ofDays(365))),
                        0.0F);

        this.discountChainNode.setNext(new TemporalDiscountChainNode(
                i-> true,
                i-> i.isBefore(Instant.now().minus(Duration.ofDays(365 * 5))),
                0.2F)
        );

    }

    public BookPageResponse getBooks(int page) {
        Page<Book> bookPage = bookRepository.findAll(PageRequest.of(page, PAGE_SIZE));

        Set<BookResponse> books = bookPage.get()
                .peek(book -> discountChainNode.setBaseDiscount(book))
                .map(book -> new BookResponse(book.getId(), book.getTitle(), book.getAuthor(), book.getDiscount()))
                .collect(Collectors.toSet());

        return new BookPageResponse(books, new PageResponse(page, bookPage.getTotalPages()));
    }

}
