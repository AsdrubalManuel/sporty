package com.sporty.controller.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ShoppingCart {

    //Items in the cart, key is book id, value is quantity
    public Map<Long, Integer> books = new HashMap<>();
    public boolean useLoyaltyPoints = false;
        
}
