package com.sporty.service;

import com.sporty.controller.dto.BookPageResponse;
import com.sporty.controller.dto.BookResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class InventoryServiceTests {

    @Autowired
    private InventoryService inventoryService;

    @Test
    public void test_loadInventoryPage() {
        BookPageResponse response = inventoryService.getBooks(0);

        assertEquals(3, response.getBooks().size());
        assertEquals(1, response.getPage().getTotalNumberOfPages());
        assertEquals(0, response.getPage().getCurrentPage());
    }

    @Test
    public void test_getOldBook() {
        BookResponse  response = inventoryService.getBook(1L);
        assertEquals(0.2F, response.getDiscount());
    }


    @Test
    public void test_getNewBook() {
        BookResponse  response = inventoryService.getBook(3L);
        assertEquals(0.0F, response.getDiscount());
    }

}
