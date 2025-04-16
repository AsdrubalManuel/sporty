package com.sporty.controller;

import com.sporty.controller.dto.BookPageResponse;
import com.sporty.controller.dto.BookRequest;
import com.sporty.controller.dto.BookResponse;
import com.sporty.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        inventoryService.deleteBook(id);
    }


    @PutMapping("/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest) {
        inventoryService.updateBook(id,bookRequest);
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> addBook(@RequestBody BookRequest bookRequest) {
       Long id = inventoryService.addBook(bookRequest);
       return ResponseEntity.ok(Map.of("id", id));
    }
}
