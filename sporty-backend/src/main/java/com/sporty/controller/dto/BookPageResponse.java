package com.sporty.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class BookPageResponse {
   private Set<BookResponse> books;
   private PageResponse page;
}
