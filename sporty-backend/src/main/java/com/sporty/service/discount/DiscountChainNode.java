package com.sporty.service.discount;

import com.sporty.model.Book;

public abstract class DiscountChainNode {

    protected DiscountChainNode next;

    public void setNext(DiscountChainNode next) {
        this.next = next;
    }

    public abstract void setBaseDiscount(Book book);

}
