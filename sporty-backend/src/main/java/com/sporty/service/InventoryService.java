package com.sporty.service;

import com.sporty.controller.dto.BookPageResponse;
import com.sporty.controller.dto.BookRequest;
import com.sporty.controller.dto.BookResponse;
import com.sporty.controller.dto.PageResponse;
import com.sporty.exception.NotFoundException;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private static final int PAGE_SIZE = 10;
    private final DiscountChainNode discountChainNode;

    @Autowired
    private BookRepository bookRepository;

    public InventoryService() {
        //Initialize discount chain

        //Adding node for settings discounts on regular books (older than 1 year but newer than 5)
        this.discountChainNode = new TemporalDiscountChainNode(
                i -> i.isAfter(Instant.now().minus(Duration.ofDays(365 * 5))),
                i -> i.isBefore(Instant.now().minus(Duration.ofDays(365))),
                0.0F);

        //Adding node for settings discounts on old books (older than 5 years)
        this.discountChainNode.setNext(new TemporalDiscountChainNode(
                i -> true,
                i -> i.isBefore(Instant.now().minus(Duration.ofDays(365 * 5))),
                0.2F)
        );
    }

    public BookPageResponse getBooks(int page) {
        Page<Book> bookPage = bookRepository.findAll(PageRequest.of(page, PAGE_SIZE));

        Set<BookResponse> books = bookPage.get()
                .peek(discountChainNode::setBaseDiscount)
                .map(book -> new BookResponse(book.getId(), book.getTitle(), book.getAuthor(), book.getDiscount()))
                .collect(Collectors.toSet());

        return new BookPageResponse(books, new PageResponse(page, bookPage.getTotalPages()));
    }

    public BookResponse getBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()) {
            throw new NotFoundException();
        }
        discountChainNode.setBaseDiscount(book.get());

        return new BookResponse(book.get().getId(), book.get().getTitle(), book.get().getAuthor(), book.get().getDiscount());
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public void updateBook(Long id, BookRequest bookRequest) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()) {
            throw new NotFoundException();
        }

        book.get().setTitle(bookRequest.getTitle());
        book.get().setAuthor(bookRequest.getAuthor());
        bookRepository.save(book.get());
    }

    public Long addBook(BookRequest bookRequest) {
        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        bookRepository.save(book);
        return book.getId();
    }

}
