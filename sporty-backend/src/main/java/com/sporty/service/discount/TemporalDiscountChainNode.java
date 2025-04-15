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

        Instant now = Instant.now();
        if (from.test(book.getCreatedAt()) && from.test(book.getCreatedAt())) {
            book.setDiscount(discount);
            return;
        }

        if (next != null) {
            next.setBaseDiscount(book);
        }
    }
}
