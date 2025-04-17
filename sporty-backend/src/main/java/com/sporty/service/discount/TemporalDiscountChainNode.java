package com.sporty.service.discount;

import com.sporty.model.Book;

import java.time.Instant;
import java.util.function.Predicate;

public class TemporalDiscountChainNode extends DiscountChainNode {
    private final Predicate<Instant> from;
    private final Predicate<Instant> to;
    private final float discount;

    public TemporalDiscountChainNode(Predicate<Instant> from, Predicate<Instant> to, float discount) {
        this.from = from;
        this.to = to;
        this.discount = discount;
    }

    @Override
    public void setBaseDiscount(Book book) {

        if (from.test(book.getCreatedAt()) && to.test(book.getCreatedAt())) {
            book.setDiscount(discount);
            return;
        }

        if (next != null) {
            next.setBaseDiscount(book);
        }
    }

    @Override
    public boolean applyLoyaltyPointsDiscount(Book book) {
        if (from.test(book.getCreatedAt()) && to.test(book.getCreatedAt())) {
            book.setDiscount(1.0F);
            return true;
        }

        if (next != null) {
            return next.applyLoyaltyPointsDiscount(book);
        }

        return false;
    }
}
