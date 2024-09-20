package com.uade.tpo.ecommerce.ecommerce.repository;

import com.uade.tpo.ecommerce.ecommerce.repository.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {
}
