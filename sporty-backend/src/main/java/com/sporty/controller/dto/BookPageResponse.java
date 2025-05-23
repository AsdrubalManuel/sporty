package com.sporty.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class BookPageResponse {
   private List<BookResponse> books;
   private PageResponse page;
}
