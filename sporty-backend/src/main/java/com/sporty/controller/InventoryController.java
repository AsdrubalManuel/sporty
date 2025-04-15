package com.sporty.controller;

import com.sporty.controller.dto.BookPageResponse;
import com.sporty.controller.dto.BookResponse;
import com.sporty.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
