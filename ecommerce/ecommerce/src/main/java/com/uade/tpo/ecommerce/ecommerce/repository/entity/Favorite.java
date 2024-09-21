package com.uade.tpo.ecommerce.ecommerce.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long favoritesId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;


    private Long productId;
}
