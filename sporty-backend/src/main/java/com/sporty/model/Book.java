package com.sporty.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private Float price;

    @CreationTimestamp
    private Instant createdAt;

    @Transient
    private Float discount = 0F;

    @Transient
    public Book clone() {
        return new Book(this.id, this.title, this.author, this.price, this.createdAt, this.discount);
    }

}
