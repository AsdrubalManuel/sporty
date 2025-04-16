package com.sporty.controller;

import com.sporty.controller.dto.BookPageResponse;
import com.sporty.controller.dto.BookResponse;
import com.sporty.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/books")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public BookPageResponse getInventory(@RequestParam(name = "page",defaultValue = "0") Integer page) {
        return inventoryService.getBooks(page);
    }

    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable Long id) {
        return inventoryService.getBook(id);
    }
}
