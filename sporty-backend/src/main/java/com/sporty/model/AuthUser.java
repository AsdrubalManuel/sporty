package com.sporty.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    @Column(columnDefinition = "integer default 0")
    private Integer loyaltyPoints;
}
