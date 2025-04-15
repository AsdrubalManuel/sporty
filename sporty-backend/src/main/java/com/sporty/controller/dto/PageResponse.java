package com.sporty.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageResponse {
    private int currentPage;
    private int totalNumberOfPages;
}
