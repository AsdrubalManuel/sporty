package com.sporty.service;

import com.sporty.controller.dto.BookPageResponse;
import com.sporty.controller.dto.BookResponse;
import com.sporty.controller.dto.PageResponse;
import com.sporty.model.Book;
import com.sporty.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private BookRepository bookRepository;

    public BookPageResponse getBooks(int page) {
        Page<Book> bookPage = bookRepository.findAll(PageRequest.of(page, PAGE_SIZE));

        Set<BookResponse> books = bookPage.get()
                .map(book -> new BookResponse(book.getId(), book.getTitle(), book.getAuthor(), 0F))
                .collect(Collectors.toSet());

        return new BookPageResponse(books, new PageResponse(page, bookPage.getTotalPages()));
    }

}
